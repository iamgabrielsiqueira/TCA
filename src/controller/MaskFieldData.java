package controller;

import javafx.scene.control.TextField;

public class MaskFieldData {
    private final TextField textField;
    private int maskSelecionada;
    public static final int TEL_8DIG = 0;
    public static final int TEL_9DIG = 1;
    public static final int CPF = 2;
    public static final int RG = 3;
    public static final int DATA = 4;

    public MaskFieldData(TextField textField) {
        this.textField = textField;
    }

    public void setMask(int maskType) {
        this.maskSelecionada = maskType;
        switch(maskType) {
            case 0:
                this.maskTel8Dig();
                break;
            case 1:
                this.maskTel9Dig();
                break;
            case 2:
                this.maskCpf();
                break;
            case 3:
                this.maskRg();
            case 4:
                this.maskData();
        }

    }

    private void maskTel8Dig() {
        this.textField.setOnKeyTyped((evento) -> {
            if (!"0123456789".contains(evento.getCharacter())) {
                evento.consume();
            }

            if (evento.getCharacter().trim().length() == 0) {
                switch(this.textField.getText().length()) {
                    case 1:
                        this.textField.setText("");
                        this.textField.positionCaret(this.textField.getText().length());
                        break;
                    case 3:
                        this.textField.setText(this.textField.getText().substring(0, 2));
                        this.textField.positionCaret(this.textField.getText().length());
                        break;
                    case 9:
                        this.textField.setText(this.textField.getText().substring(0, 8));
                        this.textField.positionCaret(this.textField.getText().length());
                }
            } else if (this.textField.getText().length() == 14) {
                evento.consume();
            }

            switch(this.textField.getText().length()) {
                case 1:
                    this.textField.setText("(" + this.textField.getText());
                    this.textField.positionCaret(this.textField.getText().length());
                    break;
                case 3:
                    this.textField.setText(this.textField.getText() + ") ");
                    this.textField.positionCaret(this.textField.getText().length());
                    break;
                case 9:
                    this.textField.setText(this.textField.getText() + "-");
                    this.textField.positionCaret(this.textField.getText().length());
            }

        });
    }

    private void maskTel9Dig() {
        this.textField.setOnKeyTyped((evento) -> {
            if (!"0123456789".contains(evento.getCharacter())) {
                evento.consume();
            }

            if (evento.getCharacter().trim().length() == 0) {
                switch(this.textField.getText().length()) {
                    case 1:
                        this.textField.setText("");
                        this.textField.positionCaret(this.textField.getText().length());
                        break;
                    case 3:
                        this.textField.setText(this.textField.getText().substring(0, 2));
                        this.textField.positionCaret(this.textField.getText().length());
                        break;
                    case 10:
                        this.textField.setText(this.textField.getText().substring(0, 9));
                        this.textField.positionCaret(this.textField.getText().length());
                }
            } else if (this.textField.getText().length() == 15) {
                evento.consume();
            }

            switch(this.textField.getText().length()) {
                case 1:
                    this.textField.setText("(" + this.textField.getText());
                    this.textField.positionCaret(this.textField.getText().length());
                    break;
                case 3:
                    this.textField.setText(this.textField.getText() + ") ");
                    this.textField.positionCaret(this.textField.getText().length());
                    break;
                case 10:
                    this.textField.setText(this.textField.getText() + "-");
                    this.textField.positionCaret(this.textField.getText().length());
            }

        });
    }

    private void maskCpf() {
        this.textField.setOnKeyTyped((evento) -> {
            if (!"0123456789".contains(evento.getCharacter())) {
                evento.consume();
            }

            if (evento.getCharacter().trim().length() == 0) {
                switch(this.textField.getText().length()) {
                    case 3:
                        this.textField.setText(this.textField.getText().substring(0, 2));
                        this.textField.positionCaret(this.textField.getText().length());
                        break;
                    case 7:
                        this.textField.setText(this.textField.getText().substring(0, 6));
                        this.textField.positionCaret(this.textField.getText().length());
                        break;
                    case 11:
                        this.textField.setText(this.textField.getText().substring(0, 9));
                        this.textField.positionCaret(this.textField.getText().length());
                }
            } else if (this.textField.getText().length() == 14) {
                evento.consume();
            }

            switch(this.textField.getText().length()) {
                case 3:
                    this.textField.setText(this.textField.getText() + ".");
                    this.textField.positionCaret(this.textField.getText().length());
                    break;
                case 7:
                    this.textField.setText(this.textField.getText() + ".");
                    this.textField.positionCaret(this.textField.getText().length());
                    break;
                case 11:
                    this.textField.setText(this.textField.getText() + "-");
                    this.textField.positionCaret(this.textField.getText().length());
            }

        });
    }

    private void maskRg() {
        this.textField.setOnKeyTyped((evento) -> {
            if (!"0123456789".contains(evento.getCharacter())) {
                evento.consume();
            }

            if (evento.getCharacter().trim().length() == 0) {
                switch(this.textField.getText().length()) {
                    case 2:
                        this.textField.setText(this.textField.getText().substring(0, 1));
                        this.textField.positionCaret(this.textField.getText().length());
                        break;
                    case 6:
                        this.textField.setText(this.textField.getText().substring(0, 5));
                        this.textField.positionCaret(this.textField.getText().length());
                        break;
                    case 10:
                        this.textField.setText(this.textField.getText().substring(0, 9));
                        this.textField.positionCaret(this.textField.getText().length());
                }
            } else if (this.textField.getText().length() == 12) {
                evento.consume();
            }

            switch(this.textField.getText().length()) {
                case 2:
                    this.textField.setText(this.textField.getText() + ".");
                    this.textField.positionCaret(this.textField.getText().length());
                    break;
                case 6:
                    this.textField.setText(this.textField.getText() + ".");
                    this.textField.positionCaret(this.textField.getText().length());
                    break;
                case 10:
                    this.textField.setText(this.textField.getText() + "-");
                    this.textField.positionCaret(this.textField.getText().length());
            }

        });
    }

    private void maskData() {
        this.textField.setOnKeyTyped((evento) -> {
            if (!"0123456789".contains(evento.getCharacter())) {
                evento.consume();
            }

            if (evento.getCharacter().trim().length() == 0) {
                switch(this.textField.getText().length()) {
                    case 2:
                        this.textField.setText(this.textField.getText().substring(0, 1));
                        this.textField.positionCaret(this.textField.getText().length());
                        break;
                    case 5:
                        this.textField.setText(this.textField.getText().substring(0, 5));
                        this.textField.positionCaret(this.textField.getText().length());
                        break;
                }
            } else if (this.textField.getText().length() == 10) {
                evento.consume();
            }

            switch(this.textField.getText().length()) {
                case 2:
                    this.textField.setText(this.textField.getText() + "/");
                    this.textField.positionCaret(this.textField.getText().length());
                    break;
                case 5:
                    this.textField.setText(this.textField.getText() + "/");
                    this.textField.positionCaret(this.textField.getText().length());
                    break;
            }

        });
    }

    public void showMask() {
        switch(this.maskSelecionada) {
            case 0:
                this.textField.setPromptText("(__) ____-____");
                break;
            case 1:
                this.textField.setPromptText("(__) _____-____");
                break;
            case 2:
                this.textField.setPromptText("___.___.___-__");
                break;
            case 3:
                this.textField.setPromptText("__.___.___-_");
            case 4:
                this.textField.setPromptText("__/__/____");
        }

    }
}
