package br.com.zup.transacoes;

public class CartaoMessage {
    private String id;
    private String email;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "CartaoMessage{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
