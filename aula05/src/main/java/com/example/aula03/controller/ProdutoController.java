package com.example.aula03.controller;

import com.example.aula03.model.entity.*;
import com.example.aula03.model.repository.PessoaRepository;
import com.example.aula03.model.repository.ProdutoRepository;
import com.example.aula03.model.repository.VendaRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Controller
@RequestMapping("produto")
@Scope("request")
public class ProdutoController {

    @Autowired
    ProdutoRepository pr;

    @Autowired
    PessoaRepository psr;

    @Autowired
    VendaRepository vr;

    @Autowired
    ItemVenda iv;

    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("produtos", pr.produtos());
        return new ModelAndView("produto/list", model);
    }

    @GetMapping("/form")
    public ModelAndView form(Produto produto){
        return new ModelAndView("/produto/form");
    }

    @GetMapping("/formulario/{id}")
    public ModelAndView formularioProduto(@PathVariable(required = false) Long id, ModelMap model) {
        Produto produto;
        if (id != null) {
            produto = pr.produto(id);
            if (produto == null) {
                return new ModelAndView("redirect:/produto/list");
            }
        } else {
            produto = new Produto();
        }
        model.addAttribute("produto", produto);
        return new ModelAndView("produto/form", model);
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarProduto(@Valid @ModelAttribute("produto") Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("/produto/form");
        }
        pr.save(produto);
        return new ModelAndView("redirect:/produto/list");
    }

    @PostMapping("/atualizar")
    public ModelAndView atualizarProduto(@Valid @ModelAttribute("produto") Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("/produto/form");
        }
        pr.update(produto);
        return new ModelAndView("redirect:/produto/list");
    }

    @PostMapping("/excluir/{id}")
    public ModelAndView excluirProduto(@PathVariable("id") Long id) {
        pr.remove(id);
        return new ModelAndView("redirect:/produto/list");
    }

    @GetMapping("/comprar")
    public ModelAndView comprar(ModelMap model) {
        model.addAttribute("produtos", pr.produtos());
        return new ModelAndView("produto/compras", model);
    }

    @PostMapping("/adicionarCarrinho/{id}")
    public ModelAndView adicionarCarrinho(@PathVariable("id") Long id, @RequestParam("quantidade") Double quantidade, HttpSession session) {
        List<ItemVenda> carrinho = (List<ItemVenda>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
            session.setAttribute("carrinho", carrinho);
        }

        Produto produto = pr.produto(id);
        iv.adicionarItem(carrinho, produto, quantidade);

        return new ModelAndView("redirect:/produto/comprar");
    }

    @ModelAttribute
    public void addCarrinhoQuantidade(ModelMap model, HttpSession session) {
        List<ItemVenda> carrinho = (List<ItemVenda>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
            session.setAttribute("carrinho", carrinho);
        }

        long quantidadeTotal = iv.contarItens(carrinho);
        model.addAttribute("quantidadeCarrinho", quantidadeTotal);
    }

    @GetMapping("/carrinho")
    public ModelAndView visualizarCarrinho(HttpSession session, ModelMap model) {
        List<ItemVenda> carrinho = (List<ItemVenda>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
            session.setAttribute("carrinho", carrinho);
        }

        List<Pessoa> pessoas = psr.pessoas(); // Obtém a lista de pessoas para o formulário

        model.addAttribute("itensCarrinho", carrinho);
        model.addAttribute("total", iv.calcularTotal(carrinho));
        model.addAttribute("pessoas", pessoas); // Adiciona a lista de pessoas ao modelo

        return new ModelAndView("produto/carrinho", model);
    }

    @PostMapping("/carrinho/remover/{id}")
    public ModelAndView removerDoCarrinho(@PathVariable("id") Long id, HttpSession session) {
        List<ItemVenda> carrinho = (List<ItemVenda>) session.getAttribute("carrinho");
        if (carrinho != null) {
            iv.removerItem(carrinho, id);
        }
        return new ModelAndView("redirect:/produto/carrinho");
    }

    @PostMapping("/finalizarVenda")
    public ModelAndView finalizarVenda(@RequestParam("pessoaId") Long pessoaId, HttpSession session, ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().stream()
                .filter(auth -> auth instanceof SimpleGrantedAuthority)
                .map(auth -> ((SimpleGrantedAuthority) auth).getAuthority())
                .findFirst().orElse("");

        List<ItemVenda> carrinho = (List<ItemVenda>) session.getAttribute("carrinho");

        if (carrinho == null || carrinho.isEmpty()) {
            model.addAttribute("erroCarrinho", "O carrinho está vazio. Adicione produtos antes de finalizar a compra.");
            return new ModelAndView("produto/carrinho", model);
        }

        Pessoa pessoa = psr.pessoa(pessoaId);
        if (pessoa == null) {
            return new ModelAndView("redirect:/produto/carrinho");
        }

        // Verifica se o usuário tem a permissão para finalizar a venda para a pessoa selecionada
        if (role.equals("ROLE_USER") && !pessoa.getId().equals(authentication.getPrincipal())) {
            model.addAttribute("erroAcesso", "Você não tem permissão para finalizar vendas para esta pessoa.");
            return new ModelAndView("produto/carrinho", model);
        }

        Venda venda = new Venda();
        venda.setData(LocalDate.now());
        venda.setPessoa(pessoa);

        List<ItemVenda> itensVenda = new ArrayList<>();
        for (ItemVenda itemCarrinho : carrinho) {
            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setProduto(itemCarrinho.getProduto());
            itemVenda.setQuantidade(itemCarrinho.getQuantidade());
            itemVenda.setVenda(venda);
            itensVenda.add(itemVenda);
        }
        venda.setListaItens(itensVenda);

        vr.save(venda);
        session.removeAttribute("carrinho");

        return new ModelAndView("redirect:/vendas/list");
    }

    @GetMapping("/procurar")
    public ModelAndView procurar(@RequestParam(defaultValue = "") String descricao, ModelMap model) {
        List<Produto> produtos = pr.produtos(descricao);
        model.addAttribute("produtos", produtos);
        model.addAttribute("descricao", descricao); // Adicionei essa linha para armazenar o valor da busca
        return new ModelAndView("produto/list", model);
    }
}
