import java.util.Scanner; 

public class Main {
    public static void main(String[] args) {
        
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

        boolean seguir_jugando; 
        
        if (bote_inicial <= 0 || bote_inicial >= 100) {
            System.out.println("El bote inicial debe ser mayor a 0 y menor o igual a 100");
            seguir_jugando = false;
            
        } else {
            System.out.println("Tu bote inicial es: " + bote_inicial);
            System.out.println("============================= " );
        
        }

        //mientras el bote inicial sea mayor a 0 y menor al exito que es 2 veces el bote inicial, se ejecuta el juego
        while(bote_inicial>0 && bote_inicial<exito && seguir_jugando) {
            
            String carta1_jugador= entregar_carta();
            String carta2_jugador= entregar_carta();
            String carta1_casa= entregar_carta();
            String carta2_casa= entregar_carta();
            int suma_acumulada_jugador = 0;
           

            String mostrar_carta1_jugador=(carta1_jugador);
            String mostrar_carta2_jugador=(carta2_jugador);
            String mostrar_carta1_casa=(carta1_casa);

            int suma_jugador = sumar_cartas(traducir_figura_a_numero(carta1_jugador), traducir_figura_a_numero(carta2_jugador));
            

            System.out.println("La primera carta del jugador es: "+(mostrar_carta1_jugador));
            System.out.println("La segunda carta del jugador es: "+(mostrar_carta2_jugador));
            System.out.println("La primera carta de la casa es: "+(mostrar_carta1_casa));
            System.out.println("La suma de tus cartas es: "+(suma_jugador));
       

         


            System.out.println("Deseas pedir carta? (true/false)");
            Scanner seguir = new Scanner(System.in);
            seguir_jugando = seguir.nextBoolean();
            while (seguir_jugando == true && suma_jugador <= 21) {
                String carta3_jugador = entregar_carta();
                System.out.println("============================== " );
                mostrar_cartas_jugador(carta3_jugador);
                suma_jugador = sumar_cartas(suma_jugador, traducir_figura_a_numero(carta3_jugador));

                if (suma_jugador < 21) {
                    System.out.println("La suma de tus cartas es: "+(suma_jugador));
                    
                }
                else if (suma_jugador == 21) {
                    System.out.println("La suma de tus cartas es: "+(suma_jugador));
                    System.out.println("BLACKJACK, GANASTE");
                    System.out.println("============================== " );
                    seguir_jugando = seguir.nextBoolean();
                }
                else{
                    System.out.println("La suma de tus cartas es: "+(suma_jugador));
                    System.out.println("Te pasaste de 21, perdiste");
                    System.out.println("============================== " );
                    System.out.println("Deseas seguir jugando? (true/false)");
                    seguir_jugando = seguir.nextBoolean();
                    
                }
            }
           
            mostrar_cartas_casa(carta2_casa);
            int suma_cartas_casa = sumar_cartas(traducir_figura_a_numero(carta1_casa), traducir_figura_a_numero(carta2_casa));
            System.out.println("La suma de las cartas de la casa es: "+(suma_cartas_casa));
            if (suma_cartas_casa >=17 ) {
                    seguir_jugando = false;
                    System.out.println("La casa se planta, no puede pedir más cartas");
                    if (suma_cartas_casa > suma_jugador && suma_cartas_casa <= 21) {
                        System.out.println("La casa gana, perdiste");
                    }
                    else if (suma_cartas_casa < suma_jugador && suma_jugador <= 21) {
                        System.out.println("Ganaste, la casa pierde");
                    }
                    else if (suma_cartas_casa == suma_jugador) {
                        System.out.println("Empate");
                    }
                    System.out.println("Deseas seguir jugando? (true/false)");
                    seguir_jugando = seguir.nextBoolean();
                }
            else if (suma_cartas_casa > 21) {
                    System.out.println("La casa se pasó de 21, ganaste");
                    
                }
            else {
                    String carta3_casa = entregar_carta();
                    mostrar_cartas_casa(carta3_casa);
                    int suma_cartas_casa2 = sumar_cartas(suma_cartas_casa, traducir_figura_a_numero(carta3_casa));
                    System.out.println("La suma de las cartas de la casa es: "+(suma_cartas_casa2));
                    System.out.println("Deseas seguir jugando? (true/false)");
                    seguir_jugando = seguir.nextBoolean();                
                }
            
        // switch (bote_inicial) {
        //     case 1:
        //         break;
        //     case 5:
        //         break;
        //     case 10:
        //         break;
        //     case 25:
        //         break;
        //     case 50:
        //         break;
        //     case 100: 
        //         if (bote_inicial >= 100) {
        //             return int apuesta = 100;
        //             break;
        //         }
        //         else {
                    
        //         }
        //     default:
        //         System.out.println("El bote inicial debe ser mayor a 0 y menor o igual a 100");
        //     }
        }    
    } 

    // traduce la figura a numero para su posterior suma, si es una figura devuelve 10, si es un as devuelve 1, si es un numero devuelve el numero
   
    public static int traducir_figura_a_numero(String numero_carta) {
        if(numero_carta == "A"){
            return 1;
        }
        else if(numero_carta == "J"){
            return 10;
        }
        else if(numero_carta == "Q"){
            return 10;
        }
        else if(numero_carta == "K"){
            return 10;
        }
        else{
            // devuelce el valor numerico de la carta si no es una figura, int convertido a String para que se pueda mostrar en pantalla
            return Integer.parseInt(numero_carta);
        }
    }
   
   // entrega la carta aleatoria, si es un numero devuelve el numero, si es una figura devuelve la figura
    public static String entregar_carta () {
        String numero_carta = String.valueOf((int)(Math.random()*13)+1);

        switch(numero_carta){
            case "1": 
                return "A";
            
            case "11": 
                return "J";

            case "12":           
                return "Q";

            case "13": 
                return "K";
            
            default:      
                return (numero_carta);
        }

    }    
    // entrega una carta aleatoria usando la función de entregar carta "aleatoria" dependiendo de la decisión del jugador:
    public static String seguirjugando (boolean seguir) {
        if (seguir) {
            return entregar_carta();
        }
        return null; //definirlo como acabar de jugar 
    }
    
    public static int sumar_cartas (int carta1, int carta2) {
        int suma = carta1 + carta2;
        return suma;
    }
    
    public static void mostrar_cartas_jugador (String carta) {
        System.out.println("Tu carta es: " + carta);
    }
    
    public static void mostrar_cartas_casa (String carta_casa) {
        System.out.println("La carta de la casa es: " + carta_casa );
    }
  
    
}
    

  