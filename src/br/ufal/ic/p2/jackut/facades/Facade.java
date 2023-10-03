package br.ufal.ic.p2.jackut.facades;

import br.ufal.ic.p2.jackut.system.Database;
import br.ufal.ic.p2.jackut.system.Session;

import java.util.ArrayList;

public class Facade {
    private Database DB = new Database();

    public void zerarSistema() {
        DB.flush();
    }

    public void criarUsuario(String login, String senha, String nome) {
        DB.newUser(login, senha, nome);
    }

    public String abrirSessao(String login, String senha) {
        return DB.startSession(login, senha);
    }

    public String getAtributoUsuario(String login, String atributo) {
        return DB.getUserAttribute(login, atributo);
    }

    public void editarPerfil(String id, String atributo, String valor) {
        DB.editProfile(id, atributo, valor);
    }

    public void adicionarAmigo(String id, String amigo) {
        DB.addFriend(id, amigo);
    }

    public boolean ehAmigo(String login, String amigo) {
        return DB.areFriends(login, amigo);
    }

    public String getAmigos(String login) {
        return DB.getFriends(login);
    }

    public void enviarRecado(String id, String destinatario, String mensagem) {
        DB.sendMessage(id, destinatario, mensagem);
    }

    public String lerRecado(String id) {
        return DB.readMessage(id);
    }

    public void encerrarSistema() {
        DB.shutdown();
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
        return DB.getCommunityMembers(nome);
    }

    public void adicionarComunidade(String sessao, String nome) {
        DB.addToCommunity(sessao, nome);
    }

    public String getComunidades(String login) {
        return DB.getUserCommunities(login);
    }

    public void enviarMensagem(String sessao, String comunidade, String mensagem) {
        DB.sendCommunityMessage(sessao, comunidade, mensagem);
    }

    public String lerMensagem(String sessao) {
        return DB.readPosts(sessao);
    }

    public boolean ehFa(String login, String idolo) {
        return DB.isFan(login, idolo);
    }

    public void adicionarIdolo(String sessao, String idolo) {
        DB.addIdol(sessao, idolo);
    }

    public String getFas (String login) {
        return DB.getFans(login);
    }

    public void adicionarPaquera(String sessao, String paquera) {
        DB.addFlirt(sessao, paquera);
    }

    public boolean ehPaquera(String login, String paquera) {
        return DB.isFlirt(login, paquera);
    }

    public String getPaqueras(String login) {
        return DB.getFlirts(login);
    }

    public void adicionarInimigo(String sessao, String inimigo) {
        DB.addEnemy(sessao, inimigo);
    }

    public void removerUsuario(String sessao) {
        DB.deleteAccount(sessao);
    }
}
