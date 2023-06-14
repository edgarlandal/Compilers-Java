package Practica9;

public class Pila {
    public static final int CAPACIDAD = 1000;
    int capacidad;
    public Tuplo[] elementos;
    int tope;

    public Pila() {/// Constructor de manera dinamica
        this(CAPACIDAD);
    }

    public Pila(int cap) {
        capacidad = cap;
        elementos = new Tuplo[cap];
        tope = 0;
    }

    public int tamano() {
        return (tope);
    }

    public boolean esVacia() {
        return (tope == 0) ? true : false;
    }

    public boolean esLlena() {
        return (tope == capacidad) ? true : false;
    }

    public void Push(Tuplo objeto) {
        try {
            if (!esLlena()) {
                elementos[tope] = objeto;
                tope++;
            } else {
                System.out.println("Pila Llena");
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException Error) {
            // TODO: handle exception
            System.out.println("Desborde de Pila");
        }
    }

    public Tuplo Pop() {/// Pop
        return (elementos[--tope]);
    }
}
