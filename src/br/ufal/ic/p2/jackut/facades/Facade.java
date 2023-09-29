package br.ufal.ic.p2.jackut.facades;
import br.ufal.ic.p2.jackut.system.Database;
import br.ufal.ic.p2.jackut.system.Session;

import java.util.ArrayList;

public class Facade {
    private Database DB = new Database();

    public void zerarSistema() {
        DB.deleteData();
        DB.flush();
    }

    public void criarUsuario(String login, String senha, String nome) {
        DB.newUser(login, senha, nome);
    }

    public String abrirSessao (String login, String senha) {
        return DB.startSession(login, senha);
    }

    public String getAtributoUsuario (String login, String atributo) {
        return DB.getUserAttribute(login, atributo);
       //usuarioTeste.getAtributoUsuario(usuarioTeste.getNome());
    }

    public void editarPerfil (String id, String atributo, String valor) {
        DB.editProfile(id, atributo, valor);
    }

    public void adicionarAmigo (String id, String amigo) {
        DB.addFriend(id, amigo);
    }

    public boolean ehAmigo (String login, String amigo) {
        return DB.areFriends(login, amigo);
    }
    public String getAmigos (String login) {
        return DB.getFriends(login);
    }
    public void enviarRecado (String id, String destinatario, String mensagem) {
        DB.sendMessage(id, destinatario, mensagem);
    }
    public String lerRecado (String id) {
        return DB.readMessage(id);
    }

    public void encerrarSistema () {
        DB.shutdown();
        DB = new Database();
        //Grava o cadastro em arquivo e encerra o programa.
        // Atingir o final de um script (final de arquivo) é equivalente a encontrar este comando.
    }

    public void criarComunidade(String sessao, String nome, String descricao) {
        DB.createComunity(sessao, nome, descricao);
    }

    public String getDescricaoComunidade(String name) {
        return DB.getCommunityDescription(name);
    }

    public String getDonoComunidade(String nome) {
        return DB.getCommunityOwner(nome);
    }

    public String getMembrosComunidade(String nome) {
        ArrayList<String> members = DB.getCommunityMembers(nome);
        members.toString();
        return "{" + String.join(",", members) + "}";
    }
}
