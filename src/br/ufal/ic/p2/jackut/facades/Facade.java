package br.ufal.ic.p2.jackut.facades;

import br.ufal.ic.p2.jackut.system.Usuario;

import java.util.ArrayList;

public class Facade {
    private DB database = new DB();
    Usuario usuarioTeste = new Usuario("", "", "");
    public void zerarSistema() {
        //Apaga todos os dados mantidos no sistema.
    }

    public void criarUsuario(String login, String senha, String nome) {
        database.newUser(login, senha, nome);
    }

    public void abrirSessao (String login, String senha) {
        //Abre uma sessão para um usuário com o login e a senha fornecidos, e retorna um id para esta sessão.
    }

    public void getAtributoUsuario (String login, String atributo) {
        usuarioTeste.getAtributoUsuario(usuarioTeste.getNome());
    }

    public void editarPerfil (String id, String atributo, String valor) {
        //Modifica o valor de um atributo do perfil de um usuário para o valor especificado.
        //Uma sessão válida (identificada por id) deve estar aberta para o usuário cujo perfil se quer editar.
    }

    public void adicionarAmigo (String id, String amigo) {
        //Adiciona um amigo ao usuário aberto na sessão especificada através de id.}
    }

    public void ehAmigo (String login, String amigo) {
        //Retorna true se os dois usuários são amigos.}
    }
    public void getAmigos (String login) {
        //Retorna a lista de amigos do usuário especificado (codificado em uma String).
    }
    public void enviarRecado (String id, String destinatario, String mensagem) {
        //Envia o recado especificado ao destinatário especificado.
        //Uma sessão válida (identificada por id) deve estar aberta para o usuário que deseja enviar o recado.}
    }
    public void lerRecado (String id) {
        //Retorna o primeiro recado da fila de recados do usuário com a sessão aberta identificada por id.}
    }

    public void encerrarSistema () {
        //Grava o cadastro em arquivo e encerra o programa.
        // Atingir o final de um script (final de arquivo) é equivalente a encontrar este comando.
    }
}
