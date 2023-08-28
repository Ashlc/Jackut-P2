package br.ufal.ic.p2.jackut.system;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class Usuario {
    private final String login;
    private final String senha;
    private final String nome;
    private ArrayList<Usuario> Usuarios = new ArrayList<>();

    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
    }

    public ArrayList<Usuario> criarUsuario(String login, String senha, String nome) {
        Usuario novoUsuario = new Usuario(login, senha, nome);
        Usuarios.add(novoUsuario);
        return Usuarios;
    }

    public void getAtributoUsuario(String nome) {
        for(Usuario usuario : Usuarios) {
            if(Objects.equals(usuario.getLogin(), nome)) {
                System.out.println(usuario.getLogin());
            }else{
                System.out.println("Usuário não cadastrado.");
            }
        }
    }

    public String getLogin() {
        return login;
    }

    public String getNome() {
        return nome;
    }

}
