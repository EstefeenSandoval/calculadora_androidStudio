package com.example.practica5_controles;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import java.util.Stack;


public class MainActivity extends AppCompatActivity {
// Componentes de la calculadora
    TextView txtResult;
    Button uno;
    Button dos;
    Button tres;
    Button cuatro;
    Button cinco;
    Button seis;
    Button siete;
    Button ocho;
    Button nueve;
    Button cero;
    Button mas;
    Button menos;
    Button masmenos;
    Button porcentaje;
    Button ac;
    Button div;
    Button mult;
    Button igual;
    Button punto;

//    Variables utilizadas para calculos
    String display = "";
    double res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.calculadora);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.suma), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Encontrar los componentes de la caluladora por id y relacionarlos con las variables antes declaradas
        txtResult = findViewById(R.id.txtResult);
        uno = findViewById(R.id.uno);
        dos = findViewById(R.id.dos);
        tres = findViewById(R.id.tres);
        cuatro = findViewById(R.id.cuatro);
        cinco = findViewById(R.id.cinco);
        seis =findViewById(R.id.seis);
        siete =findViewById(R.id.siete);
        ocho =findViewById(R.id.ocho);
        nueve =findViewById(R.id.nueve);
        cero =findViewById(R.id.cero);
        mas =findViewById(R.id.mas);
        menos =findViewById(R.id.menos);
        masmenos =findViewById(R.id.masmenos);
        porcentaje =findViewById(R.id.porcentaje);
        ac =findViewById(R.id.ac);
        div =findViewById(R.id.div);
        mult =findViewById(R.id.mult);
        igual =findViewById(R.id.igual);
        punto =findViewById(R.id.punto);

//        Agregar el listener a los botones de la calculadora
        uno.setOnClickListener(evento);
        dos.setOnClickListener(evento);
        tres.setOnClickListener(evento);
        cuatro.setOnClickListener(evento);
        cinco.setOnClickListener(evento);
        seis.setOnClickListener(evento);
        siete.setOnClickListener(evento);
        ocho.setOnClickListener(evento);
        nueve.setOnClickListener(evento);
        cero.setOnClickListener(evento);
        mas.setOnClickListener(evento);
        menos.setOnClickListener(evento);
        masmenos.setOnClickListener(evento);
        porcentaje.setOnClickListener(evento);
        ac.setOnClickListener(evento);
        div.setOnClickListener(evento);
        mult.setOnClickListener(evento);
        igual.setOnClickListener(evento);
        punto.setOnClickListener(evento);
    }

    private View.OnClickListener evento = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof Button) { // Verifica si el View es un Button
                String buttonText = ((Button) v).getText().toString();

//                Switch para determinar que boton se presiono
                switch (buttonText) {
                    case "1":
                        display = display + "1";
                        txtResult.setText(display + "");
                        break;
                    case "2":
                        display = display + "2";
                        txtResult.setText(display + "");
                        break;
                    case "3":
                        display = display + "3";
                        txtResult.setText(display + "");
                        break;
                    case "4":
                        display = display + "4";
                        txtResult.setText(display + "");
                        break;
                    case "5":
                        display = display + "5";
                        txtResult.setText(display + "");
                        break;
                    case "6":
                        display = display + "6";
                        txtResult.setText(display + "");
                        break;
                    case "7":
                        display = display + "7";
                        txtResult.setText(display + "");
                        break;
                    case "8":
                        display = display + "8";
                        txtResult.setText(display + "");
                        break;
                    case "9":
                        display = display + "9";
                        txtResult.setText(display + "");
                        break;
                    case "0":
                        display = display + "0";
                        txtResult.setText(display + "");
                        break;
                    case "/":
                        display = display + "/";
                        txtResult.setText(display + "");
                        break;
                    case "x":
                        display = display + "*";
                        txtResult.setText(display + "");
                        break;
                    case "-":
                        display = display + "-";
                        txtResult.setText(display + "");
                        break;
                    case "+":
                        display = display + "+";
                        txtResult.setText(display + "");
                        break;
                    case "AC":
                        display = "";
                        txtResult.setText(display + "");
                        break;
                    case "%":
                        display = display + "";
                        txtResult.setText(display + "");
                        break;
                    case "+/-":
                        display = display + "";
                        txtResult.setText(display + "");
                        break;
                    case ".":
                        display = display + ".";
                        txtResult.setText(display + "");
                        break;
                    case "=":
                        res = calculo(display);
                        txtResult.setText(res + "");
                        display = res + "";
                        break;
                    default:
                        txtResult.setText(buttonText);
                        break;
                }
            }
        }
    };

    public double calculo(String display){
        // Usa una pila para operadores y otra para operandos
        Stack<Double> operandos = new Stack<>();
        Stack<Character> operadores = new Stack<>();

        for (int i = 0; i < display.length(); i++) {
            char c = display.charAt(i);

            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < display.length() && (Character.isDigit(display.charAt(i)) || display.charAt(i) == '.')) {
                    sb.append(display.charAt(i++));
                }
                i--;
                operandos.push(Double.parseDouble(sb.toString()));
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operadores.isEmpty() && tienePrecedencia(c, operadores.peek())) {
                    operandos.push(operar(operadores.pop(), operandos.pop(), operandos.pop()));
                }
                operadores.push(c);
            }
        }

        while (!operadores.isEmpty()) {
            operandos.push(operar(operadores.pop(), operandos.pop(), operandos.pop()));
        }

        return operandos.pop();
    }

    private boolean tienePrecedencia(char op1, char op2) {
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        } else {
            return true;
        }
    }

    private double operar(char operador, double b, double a) {
        switch (operador) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
        }
        return 0;
    }


}