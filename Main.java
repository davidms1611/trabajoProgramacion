import java.util.Scanner; 

public class Main {
    public static void main(String[] args) {
        String[] baraja = crear_baraja();

        System.out.println("BIENVENIDO A BLACKJACK 21");
        System.out.println("Tu objetivo es duplicar el bote, suerte ");
        System.out.println("Cual es tu bote inicial?");
        Scanner bote = new Scanner(System.in);
        int bote_inicial = bote.nextInt();
        int exito = bote_inicial * 2;
        String victoria = "";
        boolean seguir_jugando = true;  
        
        if (bote_inicial <= 0 || bote_inicial > 100) {
            System.out.println("El bote inicial debe ser mayor a 0 y menor o igual a 100");
            seguir_jugando = false;
        } else {
            System.out.println("Tu bote inicial es: " + bote_inicial);
            separar('=');
        }

        // Bucle principal de rondas 
        while (bote_inicial > 0 && bote_inicial < exito && seguir_jugando) {
            System.out.println("¿Cuánto quieres apostar? Tu bote actual es: " + bote_inicial );
            
            Scanner apuesta_a_realizada = new Scanner(System.in);
            int apuesta_definida = apuesta_a_realizada.nextInt();
            
            if (apuesta_definida <= 0) {
                System.out.println("Elige un número mayor a 0 ");
                continue;
            } else if (apuesta_definida <= bote_inicial) {
                victoria = ""; 
                String carta1_jugador = entregar_carta(baraja);
                String carta2_jugador = entregar_carta(baraja);
                String carta1_casa = entregar_carta(baraja);
                String carta2_casa = entregar_carta(baraja);

                int suma_jugador = sumar_cartas(
                    traducir_figura_a_numero(carta1_jugador, true), 
                    traducir_figura_a_numero(carta2_jugador, true)
                );

                System.out.println("La primera carta del jugador es: " + carta1_jugador);
                System.out.println("La segunda carta del jugador es: " + carta2_jugador);
                System.out.println("La primera carta de la casa es: " + carta1_casa);
                System.out.println("La suma de tus cartas es: " + suma_jugador);

                Scanner seguir = new Scanner(System.in);
                boolean pedir_carta = false;

                if (suma_jugador == 21) {
                    System.out.println("BLACKJACK DE SALIDA, GANASTE");
                    separar('=');
                    victoria = "victoria";
                } else if (suma_jugador < 21) {
                    System.out.println("Deseas pedir carta? (true/false)");
                    pedir_carta = seguir.nextBoolean();
                }

                // Bucle del turno del jugador
                while (pedir_carta == true && suma_jugador < 21) {
                    String carta3_jugador = entregar_carta(baraja);
                    separar('=');
                    mostrar_cartas_jugador(carta3_jugador);
                    
                    suma_jugador = sumar_cartas(suma_jugador, traducir_figura_a_numero(carta3_jugador, true));
                    
                    if (suma_jugador == 21) {
                        System.out.println("La suma de tus cartas es: " + suma_jugador);
                        System.out.println("BLACKJACK, GANASTE");
                        separar('=');
                        victoria = "victoria";
                    } else if (suma_jugador < 21) {
                        System.out.println("La suma de tus cartas es: " + suma_jugador);
                        System.out.println("Deseas seguir jugando? (true/false)");
                        pedir_carta = seguir.nextBoolean();
                    } else {
                        System.out.println("La suma de tus cartas es: " + suma_jugador);
                        System.out.println("Te pasaste de 21, perdiste");
                        separar('=');
                        victoria = "derrota";
                    }
                }

                if (suma_jugador < 21) {
                    mostrar_cartas_casa(carta2_casa);
                    
                    int suma_cartas_casa = sumar_cartas(
                        traducir_figura_a_numero(carta1_casa, false), 
                        traducir_figura_a_numero(carta2_casa, false)
                    );
                    System.out.println("La suma de las cartas de la casa es: " + suma_cartas_casa);

                    // La casa roba cartas automáticamente hasta alcanzar al menos 17
                    while (suma_cartas_casa < 17) {
                        String carta3_casa = entregar_carta(baraja);
                        mostrar_cartas_casa(carta3_casa);
                        
                        suma_cartas_casa = sumar_cartas(suma_cartas_casa, traducir_figura_a_numero(carta3_casa, false));
                        System.out.println("La suma de las cartas de la casa es: " + suma_cartas_casa);
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

                // Actualizamos el bote al final de la ronda
                bote_inicial = calcular_dinero(victoria, apuesta_definida, bote_inicial);
                System.out.println("Dinero total: " + bote_inicial);

                if (bote_inicial > 0 && bote_inicial < exito) {
                    seguir_jugando = seguirjugando();
                }
                
                baraja = crear_baraja();
                
            } else {
                System.out.println("Elige una apuesta dentro de tu bote ");
                continue;
            } 
        }

        if (bote_inicial >= exito) {
            System.out.println("Has cumplido tu objetivo, has derrotado a la banca");
        } else if (bote_inicial <= 0) {
            System.out.println("Has perdido, la banca ha conseguido su objetivo");
        } else {
            System.out.println("Te has plantado con un bote de: " + bote_inicial);
        }
    } 

    // [CAMBIO]: Añadido el parámetro 'boolean esJugador' para distinguir si la carta es tuya o de la casa
    public static int traducir_figura_a_numero(String numero_carta, boolean esJugador) {
        switch (numero_carta) {
            case "A-C", "A-D", "A-T", "A-P":
                // [CAMBIO]: Solo te pregunta a ti si 'esJugador' es true. Si es de la casa, vale 11 automáticamente
                if (esJugador) {
                    System.out.println("Te ha salido un as, que puntuacion eliges, 1(true) o 11(false)");
                    Scanner as = new Scanner(System.in);
                    boolean eleccion_as = as.nextBoolean();
                    if (eleccion_as == true) {
                        return 1;
                    } else {
                        return 11;
                    }
                } 
                else {
                    return 11;
                }
                
            case "2-C", "2-D", "2-T", "2-P": return 2;
            case "3-C", "3-D", "3-T", "3-P": return 3;
            case "4-C", "4-D", "4-T", "4-P": return 4;
            case "5-C", "5-D", "5-T", "5-P": return 5;
            case "6-C", "6-D", "6-T", "6-P": return 6;
            case "7-C", "7-D", "7-T", "7-P": return 7;
            case "8-C", "8-D", "8-T", "8-P": return 8;
            case "9-C", "9-D", "9-T", "9-P": return 9;
            case "10-C", "10-D", "10-T", "10-P",
                 "J-C", "J-D", "J-T", "J-P",
                 "Q-C", "Q-D", "Q-T", "Q-P",
                 "K-C", "K-D", "K-T", "K-P":
                return 10;
            default:
                return 0;
        }
    }

    public static String entregar_carta(String baraja[]) {
        int posicion = (int) (Math.random() * 52);
        while (baraja[posicion] == null) {
            posicion = (int) (Math.random() * 52);
        }
        String carta_elegida = baraja[posicion];
        baraja[posicion] = null;
        return carta_elegida;
    }

    public static String[] crear_baraja() {
        String[] mazo_nuevo = {
            "A-C", "2-C", "3-C", "4-C", "5-C", "6-C", "7-C", "8-C", "9-C", "10-C", "J-C", "Q-C", "K-C",
            "A-D", "2-D", "3-D", "4-D", "5-D", "6-D", "7-D", "8-D", "9-D", "10-D", "J-D", "Q-D", "K-D",
            "A-T", "2-T", "3-T", "4-T", "5-T", "6-T", "7-T", "8-T", "9-T", "10-T", "J-T", "Q-T", "K-T",
            "A-P", "2-P", "3-P", "4-P", "5-P", "6-P", "7-P", "8-P", "9-P", "10-P", "J-P", "Q-P", "K-P"
        };
        return mazo_nuevo;
    }
       
    public static boolean seguirjugando() {
        System.out.println("¿Quieres seguir jugando (true/false)?");
        Scanner jugar = new Scanner(System.in);
        boolean jugar_de_nuevo = jugar.nextBoolean();
        return jugar_de_nuevo;
    }
    
    public static int sumar_cartas(int carta1, int carta2) {
        int suma = carta1 + carta2;
        return suma;
    }
    
    public static void mostrar_cartas_jugador(String carta) {
        System.out.println("Tu carta es: " + carta);
    }
    
    public static void mostrar_cartas_casa(String carta_casa) {
        System.out.println("La carta de la casa es: " + carta_casa);
    }
    
    public static int calcular_dinero(String victoria, int apuesta, int bote_inicial) {
        int dinero_final = 0;
        switch (victoria) {
            case "victoria":
                dinero_final = bote_inicial + apuesta;
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
    
    public static void separar(char caracter) {
        for (int i = 0; i <= 15; i++) {
            System.out.print(caracter);
        }
        System.out.println("");
    }
}