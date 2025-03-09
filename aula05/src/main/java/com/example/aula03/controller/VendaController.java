package com.example.aula03.controller;

import com.example.aula03.model.entity.Venda;
import com.example.aula03.model.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Transactional
@Controller
@RequestMapping("vendas")
public class VendaController {
    @Autowired
    VendaRepository vr;

    @GetMapping("/list")
    public ModelAndView listar (ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();

        // Verifica se o usuário tem o papel ADMIN
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        List<Venda> vendas;

        if (isAdmin) {
            // Se for administrador, retorna todas as vendas
            vendas = vr.findAll();
        } else {
            // Se não for administrador, retorna apenas as vendas associadas ao usuário
            vendas = vr.findByPessoaUsername(username); // Assumindo que o método está definido no repositório
        }

        model.addAttribute("venda", vendas);
        return new ModelAndView("venda/list", model);
    }

    @GetMapping ("/detalhes/{id}")
    public ModelAndView detalharVenda(@PathVariable("id") Long id, ModelMap model) {
        Venda venda = vr.venda(id);

        model.addAttribute("venda", venda);
        model.addAttribute("total", venda.total());
        model.addAttribute("itens", venda.getListaItens());

        return new ModelAndView("venda/detalhar", model);

    }
    @GetMapping("/procurar")
    public ModelAndView procurar(@RequestParam(defaultValue = "") String nomeCliente, ModelMap model) {
        List<Venda> vendas = vr.vendas(nomeCliente);
        model.addAttribute("venda", vendas);
        model.addAttribute("nomeCliente", nomeCliente); // Adicionei essa linha para armazenar o valor da busca
        return new ModelAndView("venda/list", model);
    }

    // Procurar venda por data
    @GetMapping("/data")
    public ModelAndView data(@RequestParam(defaultValue = "") String dataInicio,
                             @RequestParam(defaultValue = "") String dataFim,
                             ModelMap model) {

        List<Venda> vendas = vr.data(dataInicio, dataFim);
        model.addAttribute("venda", vendas);
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim", dataFim);
        return new ModelAndView("venda/list", model);

    }

}
