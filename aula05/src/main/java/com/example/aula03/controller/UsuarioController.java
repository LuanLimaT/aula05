package com.example.aula03.controller;

import com.example.aula03.model.entity.Pessoa;
import com.example.aula03.model.entity.PessoaFisica;
import com.example.aula03.model.entity.PessoaJuridica;
import com.example.aula03.model.entity.Role;
import com.example.aula03.model.entity.Usuario;
import com.example.aula03.model.repository.PessoaRepository;
import com.example.aula03.model.repository.RoleRepository;
import com.example.aula03.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("usuario")
@Transactional
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pr;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model) {
        return "autenticacao/cadastrar";
    }

    @PostMapping("/cadastrar")
    public String cadastrarUsuario(@RequestParam String pessoaTipo, @RequestParam String username,
                                   @RequestParam String password, @RequestParam(required = false) String cpf,
                                   @RequestParam(required = false) String nome, @RequestParam(required = false) String cnpj,
                                   @RequestParam(required = false) String razaoSocial, @RequestParam(required = false) String telefone,
                                   Model model) {

        Pessoa pessoa = null;

        if ("FISICA".equals(pessoaTipo)) {
            if (cpf == null || nome == null) {
                model.addAttribute("error", "Dados insuficientes para Pessoa Física.");
                return "autenticacao/cadastrar";
            }
            PessoaFisica pessoaFisica = new PessoaFisica();
            pessoaFisica.setCpf(cpf);
            pessoaFisica.setNome(nome);
            pessoaFisica.setTelefone(telefone);
            pessoa = pr.save(pessoaFisica);
        } else if ("JURIDICA".equals(pessoaTipo)) {
            if (cnpj == null || razaoSocial == null) {
                model.addAttribute("error", "Dados insuficientes para Pessoa Jurídica.");
                return "autenticacao/cadastrar";
            }
            PessoaJuridica pessoaJuridica = new PessoaJuridica();
            pessoaJuridica.setCnpj(cnpj);
            pessoaJuridica.setNome(razaoSocial);
            pessoaJuridica.setTelefone(telefone);
            pessoa = pr.save(pessoaJuridica);
        } else {
            model.addAttribute("error", "Tipo de pessoa inválido.");
            return "autenticacao/cadastrar";
        }

        Usuario usuario = new Usuario();
        usuario.setLogin(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setPessoa(pessoa); // Definindo a pessoa

        // Associar o papel ROLE_USER ao usuário
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = new Role();
            role.setNome("ROLE_USER");
            roleRepository.save(role);
        }

        usuario.setRoles(List.of(role)); // Definindo os papéis do usuário

        usuarioRepository.save(usuario);

        model.addAttribute("success", "Usuário cadastrado com sucesso.");
        return "redirect:/login";
    }
}