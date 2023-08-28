package br.ufal.ic.p2.jackut.facades;
import br.ufal.ic.p2.jackut.system.Database;

public class Facade {
    private final Database DB = new Database();

    public void zerarSistema() {
        DB.flush();
    }

    public void criarUsuario(String login, String senha, String nome) {
        DB.newUser(login, senha, nome);
    }

    public void abrirSessao (String login, String senha) {
        DB.startSession(login, senha);
    }

    public String getAtributoUsuario (String login, String atributo) {
        return DB.getUserAttribute(login, atributo);
       //usuarioTeste.getAtributoUsuario(usuarioTeste.getNome());
    }

    public void editarPerfil (String id, String atributo, String valor) {
        //Modifica o valor de um atributo do perfil de um usu�rio para o valor especificado.
        //Uma sess�o v�lida (identificada por id) deve estar aberta para o usu�rio cujo perfil se quer editar.
    }

    public void adicionarAmigo (String id, String amigo) {
        //Adiciona um amigo ao usu�rio aberto na sess�o especificada atrav�s de id.}
    }

    public void ehAmigo (String login, String amigo) {
        //Retorna true se os dois usu�rios s�o amigos.}
    }
    public void getAmigos (String login) {
        //Retorna a lista de amigos do usu�rio especificado (codificado em uma String).
    }
    public void enviarRecado (String id, String destinatario, String mensagem) {
        //Envia o recado especificado ao destinat�rio especificado.
        //Uma sess�o v�lida (identificada por id) deve estar aberta para o usu�rio que deseja enviar o recado.}
    }
    public void lerRecado (String id) {
        //Retorna o primeiro recado da fila de recados do usu�rio com a sess�o aberta identificada por id.}
    }

    public void encerrarSistema () {
        //Grava o cadastro em arquivo e encerra o programa.
        // Atingir o final de um script (final de arquivo) � equivalente a encontrar este comando.
    }
}
