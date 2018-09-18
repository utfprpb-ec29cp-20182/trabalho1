package sd29cp.trabalho1.interfaces;


public interface IDNS {

    public String bind(String dominio, String IP, int porta) throws DominioJaRegistrado;

    public String lookup(String dominio) throws DominioNaoRegistrado;

    public String unbind(String dominio) throws DominioNaoRegistrado;

    public String[] list();
}
