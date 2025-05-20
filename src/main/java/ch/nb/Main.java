package ch.nb;

import ch.nb.auth.DocEcmApiAuth;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DocEcmApiAuth.authenticate();
        DocEcmApiAuth.isTokenExpired(60);
        System.out.println(DocEcmApiAuth.currentToken.getExpiresAt());


    }
}