package br.ufal.ic.p2.jackut.facades;
import br.ufal.ic.p2.jackut.system.Database;

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
        //Envia o recado especificado ao destinatário especificado.
        //Uma sessão válida (identificada por id) deve estar aberta para o usuário que deseja enviar o recado.}
    }
    public void lerRecado (String id) {
        //Retorna o primeiro recado da fila de recados do usuário com a sessão aberta identificada por id.}
    }

    public void encerrarSistema () {
        DB.shutdown();
        DB = new Database();
        //Grava o cadastro em arquivo e encerra o programa.
        // Atingir o final de um script (final de arquivo) é equivalente a encontrar este comando.
    }
}
