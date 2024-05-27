package com.cesar.edunave;

import com.cesar.edunave.login.LoginService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout{
    private LoginService loginService;

    public MainView() {
        loginService = new LoginService();

        TextField emailField = new TextField("Email");
        PasswordField senhaField = new PasswordField("Senha");
        Button loginButton = new Button("Login");

        loginButton.addClickListener(event -> {
            String email = emailField.getValue();
            String senha = senhaField.getValue();
            if (loginService.validarCredenciais(email, senha)) {
                Notification.show("Login bem-sucedido!");
            } else {
                Notification.show("Credenciais inválidas.");
            }
        });

        add(emailField, senhaField, loginButton);
    }

}
