package com.example.aula03.controller;

import com.example.aula03.model.entity.Pessoa;
import com.example.aula03.model.entity.PessoaFisica;
import com.example.aula03.model.entity.PessoaJuridica;
import com.example.aula03.model.repository.PessoaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Transactional
@Controller
@RequestMapping("pessoa")
public class PessoaController {

    @Autowired
    PessoaRepository pr;

    @GetMapping("/form/{id}")
    public ModelAndView form(@PathVariable("id") Long id, ModelMap model) {
        Pessoa pessoa = pr.pessoa(id);
        if (pessoa == null) {
            return new ModelAndView("redirect:/pessoa/list");
        }
        model.addAttribute("pessoa", pessoa);
        if (pessoa instanceof PessoaFisica) {
            return new ModelAndView("/pessoa/form_fisica", model);
        } else if (pessoa instanceof PessoaJuridica) {
            return new ModelAndView("/pessoa/form_juridica", model);
        } else {
            return new ModelAndView("redirect:/pessoa/list");
        }
    }

    @GetMapping("/form/fisica")
    public ModelAndView formFisica(ModelMap model) {
        model.addAttribute("pessoa", new PessoaFisica());
        return new ModelAndView("/pessoa/form_fisica");
    }
    @GetMapping("/form/juridica")
    public ModelAndView formJuridica(ModelMap model) {
        model.addAttribute("pessoa", new PessoaJuridica());
        return new ModelAndView("/pessoa/form_juridica");
    }

    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("pessoas", pr.pessoas());
        return new ModelAndView("pessoa/list", model); // Caminho para a view
    }

    @PostMapping("/save/fisica")
    public ModelAndView savePessoaFisica(@Valid @ModelAttribute("pessoa") PessoaFisica pessoa, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("/pessoa/form_fisica");
        }
        pr.save(pessoa);
        return new ModelAndView("redirect:/pessoa/list");
    }

    @PostMapping("/save/juridica")
    public ModelAndView savePessoaJuridica(@Valid @ModelAttribute("pessoa") PessoaJuridica pessoa, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("/pessoa/form_juridica");
        }
        pr.save(pessoa);
        return new ModelAndView("redirect:/pessoa/list");
    }

    @PostMapping("/update/juridica")
    public ModelAndView updatePessoaJuridica(@Valid @ModelAttribute("pessoa") PessoaJuridica pessoa, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("/pessoa/form_juridica", new ModelMap().addAttribute("pessoa", pessoa).addAttribute("org.springframework.validation.BindingResult.pessoa", result));
        }
        pr.update(pessoa);
        return new ModelAndView("redirect:/pessoa/list");
    }

    @PostMapping("/update/fisica")
    public ModelAndView updatePessoaFisica(@Valid @ModelAttribute("pessoa") PessoaFisica pessoa, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("/pessoa/form_fisica", new ModelMap().addAttribute("pessoa", pessoa).addAttribute("org.springframework.validation.BindingResult.pessoa", result));
        }
        pr.update(pessoa);
        return new ModelAndView("redirect:/pessoa/list");
    }

    @PostMapping("/excluir/{id}")
    public ModelAndView excluirPessoa(@PathVariable("id") Long id, ModelMap model) {
        try {
            pr.remove(id);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("erroExcluir", "Não é possível excluir a pessoa. Verifique se ela está associada a alguma venda.");
        }
        return new ModelAndView("redirect:/pessoa/list", model);
    }

    @GetMapping("/procurar")
    public ModelAndView procurar(@RequestParam(defaultValue = "") String nome, ModelMap model) {
        List<Pessoa> pessoas = pr.pessoas(nome);
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("nome", nome); // Adicionei essa linha para armazenar o valor da busca
        return new ModelAndView("pessoa/list", model);

    }

}
