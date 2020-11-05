package com.main;

import org.linguafranca.pwdb.Entry;
import org.linguafranca.pwdb.Visitor;

public class TestVisitor extends Visitor.Default {

    private final Credentials.Builder credentialsBuilder = new Credentials.Builder();

    @Override
    public void visit(Entry entry) {
        String title = entry.getProperty("Title");

        switch (title) {
            case "kontonummer":
                credentialsBuilder.kontonummer(entry.getPassword());
                break;
            case "key":
                credentialsBuilder.key(entry.getPassword());
                break;
            case "credentials":
                credentialsBuilder.zugangsnummer(entry.getUsername());
                credentialsBuilder.pin(entry.getPassword());
                break;
        }

        super.visit(entry);
    }

    public Credentials getCredentials() {
        return credentialsBuilder.build();
    }
}
