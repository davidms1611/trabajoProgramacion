import java.util.Scanner; 

public class Main {
    public static void main(String[] args) {
        String[] baraja = {
        "A-C", "2-C", "3-C", "4-C", "5-C", "6-C", "7-C", "8-C", "9-C", "10-C", "J-C", "Q-C", "K-C",
        "A-D", "2-D", "3-D", "4-D", "5-D", "6-D", "7-D", "8-D", "9-D", "10-D", "J-D", "Q-D", "K-D",
        "A-T", "2-T", "3-T", "4-T", "5-T", "6-T", "7-T", "8-T", "9-T", "10-T", "J-T", "Q-T", "K-T",
        "A-P", "2-P", "3-P", "4-P", "5-P", "6-P", "7-P", "8-P", "9-P", "10-P", "J-P", "Q-P", "K-P"
        };
        
        int ficha_1 = 1;
        int ficha_5 = 5;
        int ficha_10 = 10;
        int ficha_25 = 25;
        int ficha_50 = 50;
        int ficha_100 = 100;

        System.out.println("BIENVENIDO A BALCKJACK 21");
        System.out.println("Cual es tu bote inicial?");
        Scanner bote = new Scanner(System.in);
        int bote_inicial = bote.nextInt();
        int exito = bote_inicial * 2;
        String victoria = "";
        boolean seguir_jugando = true;  
        
        // Validamos el rango antes de empezar para evitar entrar al bucle si es inválido
        if (bote_inicial <= 0 || bote_inicial > 100) {
            System.out.println("El bote inicial debe ser mayor a 0 y menor o igual a 100");
            seguir_jugando = false;
        } else {
            System.out.println("Tu bote inicial es: " + bote_inicial);
            System.out.println("============================= ");
        }

        // Bucle principal de rondas 
        while (bote_inicial > 0 && bote_inicial < exito && seguir_jugando) {
            victoria = ""; 

            String carta1_jugador = entregar_carta(baraja);
            String carta2_jugador = entregar_carta(baraja);
            String carta1_casa = entregar_carta(baraja);
            String carta2_casa = entregar_carta(baraja);

            int suma_acumulada_jugador = 0;

            String mostrar_carta1_jugador = (carta1_jugador);
            String mostrar_carta2_jugador = (carta2_jugador);
            String mostrar_carta1_casa = (carta1_casa);

            int suma_jugador = sumar_cartas(traducir_figura_a_numero(carta1_jugador), traducir_figura_a_numero(carta2_jugador));

            System.out.println("La primera carta del jugador es: " + (mostrar_carta1_jugador));
            System.out.println("La segunda carta del jugador es: " + (mostrar_carta2_jugador));
            System.out.println("La primera carta de la casa es: " + (mostrar_carta1_casa));
            System.out.println("La suma de tus cartas es: " + (suma_jugador));

            Scanner seguir = new Scanner(System.in);
            boolean pedir_carta = false;

            // Solo preguntamos si quiere carta cuando aún no ha llegado a 21
            if (suma_jugador < 21) {
                System.out.println("Deseas pedir carta? (true/false)");
                pedir_carta = seguir.nextBoolean();
            }

            // Bucle del turno del jugador: pide cartas mientras quiera y no llegue a 21
            while (pedir_carta == true && suma_jugador < 21) {
                String carta3_jugador = entregar_carta(baraja);
                System.out.println("============================== ");
                mostrar_cartas_jugador(carta3_jugador);
                suma_jugador = sumar_cartas(suma_jugador, traducir_figura_a_numero(carta3_jugador));

                if (suma_jugador < 21) {
                    System.out.println("La suma de tus cartas es: " + (suma_jugador));
                    System.out.println("Deseas seguir jugando? (true/false)");
                    pedir_carta = seguir.nextBoolean();
                } else if (suma_jugador == 21) {
                    System.out.println("La suma de tus cartas es: " + (suma_jugador));
                    System.out.println("BLACKJACK, GANASTE");
                    System.out.println("============================== ");
                    victoria = "victoria";
                } else {
                    System.out.println("La suma de tus cartas es: " + (suma_jugador));
                    System.out.println("Te pasaste de 21, perdiste");
                    System.out.println("============================== ");
                    victoria = "derrota";
                }
            }

            // Si el jugador se pasó de 21, la casa no juega su turno
            if (suma_jugador <= 21) {
                mostrar_cartas_casa(carta2_casa);
                int suma_cartas_casa = sumar_cartas(traducir_figura_a_numero(carta1_casa), traducir_figura_a_numero(carta2_casa));
                System.out.println("La suma de las cartas de la casa es: " + (suma_cartas_casa));

                // La casa roba cartas automáticamente hasta alcanzar al menos 17
                while (suma_cartas_casa < 17) {
                    String carta3_casa = entregar_carta(baraja);
                    mostrar_cartas_casa(carta3_casa);
                    suma_cartas_casa = sumar_cartas(suma_cartas_casa, traducir_figura_a_numero(carta3_casa));
                    System.out.println("La suma de las cartas de la casa es: " + (suma_cartas_casa));
                }

                // Comparación final entre el jugador y la casa
                if (suma_cartas_casa > 21) {
                    System.out.println("La casa se pasó de 21, ganaste");
                    victoria = "victoria";
                } else {
                    System.out.println("La casa se planta, no puede pedir más cartas");
                    if (suma_cartas_casa > suma_jugador) {
                        System.out.println("La casa gana, perdiste");
                        victoria = "derrota";
                    } else if (suma_cartas_casa < suma_jugador) {
                        System.out.println("Ganaste, la casa pierde");
                        victoria = "victoria";
                    } else {
                        System.out.println("Empate");
                        victoria = "empate";
                    }
                }
            }

            // Actualizamos el bote  al final de la ronda una vez definido el ganador
            bote_inicial = calcular_dinero(victoria, 10, bote_inicial);
            System.out.println("Dinero total: " + bote_inicial);

            // Solo preguntamos si quiere jugar otra ronda si aún tiene saldo y no ha alcanzado el objetivo
            if (bote_inicial > 0 && bote_inicial < exito) {
                seguir_jugando = seguirjugando();
            }
            
            baraja = crear_baraja();
        }
    } 

    // Convierte el texto de la carta (ej. "K-P") en su valor numérico para poder sumarlo
    public static int traducir_figura_a_numero(String numero_carta) {
        switch (numero_carta) {
            case "A-C", "A-D", "A-T", "A-P":
                return 1;
            case "2-C", "2-D", "2-T", "2-P":
                return 2;
            case "3-C", "3-D", "3-T", "3-P":
                return 3;
            case "4-C", "4-D", "4-T", "4-P":
                return 4;
            case "5-C", "5-D", "5-T", "5-P":
                return 5;
            case "6-C", "6-D", "6-T", "6-P":
                return 6;
            case "7-C", "7-D", "7-T", "7-P":
                return 7;
            case "8-C", "8-D", "8-T", "8-P":
                return 8;
            case "9-C", "9-D", "9-T", "9-P":
                return 9;
            case "10-C", "10-D", "10-T", "10-P",
                 "J-C", "J-D", "J-T", "J-P",
                 "Q-C", "Q-D", "Q-T", "Q-P",
                 "K-C", "K-D", "K-T", "K-P":
                return 10;
            default:
                return 0;
        }
    }

    // Extrae una carta al azar de la baraja y la marca como null para evitar repetirla
    public static String entregar_carta(String baraja[]) {
        int posicion;
        do {
            posicion = (int) (Math.random() * 52);
        } while (baraja[posicion] == null);

        String carta_elegida = baraja[posicion];
        baraja[posicion] = null;

        return carta_elegida;
    }

    // Genera un mazo nuevo de 52 cartas para reiniciar la baraja en cada ronda
    public static String[] crear_baraja() {
        String[] mazo_nuevo = {
        "A-C", "2-C", "3-C", "4-C", "5-C", "6-C", "7-C", "8-C", "9-C", "10-C", "J-C", "Q-C", "K-C",
        "A-D", "2-D", "3-D", "4-D", "5-D", "6-D", "7-D", "8-D", "9-D", "10-D", "J-D", "Q-D", "K-D",
        "A-T", "2-T", "3-T", "4-T", "5-T", "6-T", "7-T", "8-T", "9-T", "10-T", "J-T", "Q-T", "K-T",
        "A-P", "2-P", "3-P", "4-P", "5-P", "6-P", "7-P", "8-P", "9-P", "10-P", "J-P", "Q-P", "K-P"
        };
        return mazo_nuevo;
    }
       
    // Pregunta por consola si el jugador desea iniciar una nueva ronda
    public static boolean seguirjugando() {
        System.out.println("¿quieres seguir jugando (true/false)?");
        Scanner jugar = new Scanner(System.in);
        boolean jugar_de_nuevo = jugar.nextBoolean();
        return jugar_de_nuevo;
    }
    
    // Suma dos valores numéricos de cartas
    public static int sumar_cartas(int carta1, int carta2) {
        int suma = carta1 + carta2;
        return suma;
    }
    
    // Muestra por pantalla la carta recibida por el jugador
    public static void mostrar_cartas_jugador(String carta) {
        System.out.println("Tu carta es: " + carta);
    }
    
    // Muestra por pantalla la carta descubierta o robada por la casa
    public static void mostrar_cartas_casa(String carta_casa) {
        System.out.println("La carta de la casa es: " + carta_casa);
    }
    
    // Calcula y devuelve el nuevo saldo según el resultado de la ronda
    public static int calcular_dinero(String victoria, int apuesta, int bote_inicial) {
        int dinero_final = 0;
        switch (victoria) {
            case "victoria":
                dinero_final = bote_inicial + apuesta * 2;
                break;
            case "derrota":
                dinero_final = bote_inicial - apuesta;
                break;
            case "empate":
                dinero_final = bote_inicial;
                break;
            default:
                break;
        }
        return dinero_final;
    }
}