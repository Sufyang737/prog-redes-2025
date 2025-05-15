package ar.edu.et32.solucionGuiaEjercicios;

import ar.edu.et32.utils.InputUtils;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Locale;

public class SolucionGuiaEjercicios {

    private final PrintStream out = System.out;

    public void mostrarMenu() {
        out.println("\n--- MENÚ DE EJERCICIOS ---");
        out.println("--- Grupo 1 (Entrada: System.in.read()) ---");
        out.println("1. Calcular Sueldo Bruto");
        out.println("2. Calcular Ángulo Restante de Triángulo");
        out.println("3. Calcular Perímetro de Cuadrado (dada superficie)");
        out.println("4. Convertir Fahrenheit a Centígrados");
        out.println("5. Convertir Segundos a Días, Horas, Minutos, Segundos");
        out.println("6. Calcular Planes de Pago de Artículo");
        out.println("7. Mostrar Mes de Nacimiento por Signo Zodiacal");
        out.println("\n--- Grupo 2 (Entrada: Reader/BufferedReader) ---");
        out.println("8. Ordenar Tres Apellidos Alfabéticamente");
        out.println("9. Indicar Menor de Cuatro Números Reales");
        out.println("10. Indicar si Número es Par o Impar");
        out.println("11. Indicar si Mayor de Dos Reales es Divisible por el Menor");
        out.println("12. Mostrar Signo Zodiacal por Fecha de Nacimiento");
        out.println("13. Indicar Persona con Apellido Más Largo");
        out.println("14. Mostrar Tabla de Multiplicar de un Número");
        out.println("15. Indicar si Número Natural es Primo");
        out.println("------------------------------------");
        out.println("16. Salir");
        out.println("------------------------------------");
    }

    public int leerOpcionMenu() {
        return InputUtils.leerEnteroValidadoSystemIn("Seleccione una opción (1-16): ", 1, 16);
    }

    public void presioneEnterParaContinuar() {
        InputUtils.leerLineaSystemIn("Presione Enter para continuar...");
    }

    public void resolverEjercicio1() {
        out.println("\n--- Ejercicio 1: Sueldo Bruto ---");
        double valorHora = InputUtils.leerDoubleValidadoSystemIn("Ingrese el valor de una hora de trabajo: ", 0.001, null);
        double horasTrabajadas = InputUtils.leerDoubleValidadoSystemIn("Ingrese la cantidad de horas trabajadas: ", 0.0, null);
        double sueldoBruto = valorHora * horasTrabajadas;
        out.printf(Locale.US, "El sueldo bruto es: $%.2f%n", sueldoBruto);
    }

    public void resolverEjercicio2() {
        out.println("\n--- Ejercicio 2: Ángulo Restante Triángulo ---");
        double angulo1 = 0, angulo2 = 0;
        boolean datosValidos = false;

        while (!datosValidos) {
            angulo1 = InputUtils.leerDoubleValidadoSystemIn("Ingrese el valor del primer ángulo (mayor a 0 y menor a 180): ", 0.0000001, 179.9999999);
            angulo2 = InputUtils.leerDoubleValidadoSystemIn("Ingrese el valor del segundo ángulo (mayor a 0 y menor a 180): ", 0.0000001, 179.9999999);

            if (angulo1 + angulo2 < 180.0) {
                datosValidos = true;
            } else {
                out.println("La suma de los dos ángulos (" + String.format(Locale.US, "%.2f + %.2f = %.2f", angulo1, angulo2, angulo1 + angulo2) + ") debe ser menor a 180. Intente de nuevo.");
            }
        }
        double angulo3 = 180.0 - angulo1 - angulo2;
        out.printf(Locale.US, "El valor del ángulo restante es: %.2f grados%n", angulo3);
    }

    public void resolverEjercicio3() {
        out.println("\n--- Ejercicio 3: Perímetro Cuadrado ---");
        double superficie = InputUtils.leerDoubleValidadoSystemIn("Ingrese la superficie del cuadrado (m2, mayor a 0): ", 0.00001, null);
        double lado = Math.sqrt(superficie);
        double perimetro = 4 * lado;
        out.printf(Locale.US, "El perímetro del cuadrado es: %.2f m%n", perimetro);
    }

    public void resolverEjercicio4() {
        out.println("\n--- Ejercicio 4: Fahrenheit a Centígrados ---");
        double tempF = InputUtils.leerDoubleValidadoSystemIn("Ingrese la temperatura en grados Fahrenheit: ", null, null);
        double tempC = (tempF - 32.0) * 5.0 / 9.0;
        out.printf(Locale.US, "La temperatura en grados Centígrados es: %.2f °C%n", tempC);
    }

    public void resolverEjercicio5() {
        out.println("\n--- Ejercicio 5: Tiempo a Días, Horas, Minutos, Segundos ---");
        int totalSegundos = InputUtils.leerEnteroValidadoSystemIn("Ingrese el tiempo total en segundos (entero no negativo): ", 0, null);

        int dias = totalSegundos / (24 * 60 * 60);
        int segundosRestantes = totalSegundos % (24 * 60 * 60);
        int horas = segundosRestantes / (60 * 60);
        segundosRestantes %= (60 * 60);
        int minutos = segundosRestantes / 60;
        int segundos = segundosRestantes % 60;

        out.println("El tiempo expresado es:");
        out.println("Días: " + dias);
        out.println("Horas: " + horas);
        out.println("Minutos: " + minutos);
        out.println("Segundos: " + segundos);
    }

    public void resolverEjercicio6() {
        out.println("\n--- Ejercicio 6: Planes de Pago ---");
        double precioPublicado = InputUtils.leerDoubleValidadoSystemIn("Ingrese el precio publicado del artículo (mayor a 0): $", 0.001, null);

        out.println("\nPlan 1: 100% al contado (10% descuento).");
        out.printf(Locale.US, "  Total a pagar: $%.2f%n", precioPublicado * 0.90);

        double precioPlan2 = precioPublicado * 1.10;
        out.println("\nPlan 2: 50% contado, resto 2 cuotas (precio +10%).");
        out.printf(Locale.US, "  Precio base plan: $%.2f%n", precioPlan2);
        out.printf(Locale.US, "  Contado (50%%): $%.2f%n", precioPlan2 * 0.50);
        out.printf(Locale.US, "  Cada una de 2 cuotas: $%.2f%n", (precioPlan2 * 0.50) / 2.0);

        double precioPlan3 = precioPublicado * 1.15;
        out.println("\nPlan 3: 25% contado, resto 5 cuotas (precio +15%).");
        out.printf(Locale.US, "  Precio base plan: $%.2f%n", precioPlan3);
        out.printf(Locale.US, "  Contado (25%%): $%.2f%n", precioPlan3 * 0.25);
        out.printf(Locale.US, "  Cada una de 5 cuotas: $%.2f%n", (precioPlan3 * 0.75) / 5.0);

        double precioPlan4 = precioPublicado * 1.25;
        out.println("\nPlan 4: Financiado 8 cuotas (precio +25%).");
        out.printf(Locale.US, "  Precio base plan: $%.2f%n", precioPlan4);
        out.printf(Locale.US, "  Cuotas 1-4 (c/u del 60%%/4): $%.2f%n", (precioPlan4 * 0.60) / 4.0);
        out.printf(Locale.US, "  Cuotas 5-8 (c/u del 40%%/4): $%.2f%n", (precioPlan4 * 0.40) / 4.0);
    }

    public void resolverEjercicio7() {
        out.println("\n--- Ejercicio 7: Mes de Nacimiento por Signo Zodiacal ---");
        String signoInput = InputUtils.leerStringNoVacioSystemIn("Ingrese su signo zodiacal: ");
        String signo = signoInput.toLowerCase().trim();
        String mesAproximado;

        switch (signo) {
            case "aries": mesAproximado = "Marzo - Abril"; break;
            case "tauro": mesAproximado = "Abril - Mayo"; break;
            case "geminis": case "géminis": mesAproximado = "Mayo - Junio"; break;
            case "cancer": case "cáncer": mesAproximado = "Junio - Julio"; break;
            case "leo": mesAproximado = "Julio - Agosto"; break;
            case "virgo": mesAproximado = "Agosto - Septiembre"; break;
            case "libra": mesAproximado = "Septiembre - Octubre"; break;
            case "escorpio": mesAproximado = "Octubre - Noviembre"; break;
            case "sagitario": mesAproximado = "Noviembre - Diciembre"; break;
            case "capricornio": mesAproximado = "Diciembre - Enero"; break;
            case "acuario": mesAproximado = "Enero - Febrero"; break;
            case "piscis": mesAproximado = "Febrero - Marzo"; break;
            default:
                out.println("Signo '" + signoInput + "' no reconocido.");
                return;
        }
        String signoCapitalizado = signo.substring(0, 1).toUpperCase() + signo.substring(1);
        out.println("El mes de nacimiento aproximado para " + signoCapitalizado + " es: " + mesAproximado);
    }

    // --- Grupo 2: BufferedReader vía InputUtils ---
    public void resolverEjercicio8() {
        out.println("\n--- Ejercicio 8: Ordenar Tres Apellidos ---");
        String apellido1 = InputUtils.leerStringNoVacioBufferedReader("Ingrese el primer apellido: ");
        String apellido2 = InputUtils.leerStringNoVacioBufferedReader("Ingrese el segundo apellido: ");
        String apellido3 = InputUtils.leerStringNoVacioBufferedReader("Ingrese el tercer apellido: ");

        String[] apellidos = {apellido1, apellido2, apellido3};
        Arrays.sort(apellidos, String.CASE_INSENSITIVE_ORDER);

        out.println("Apellidos ordenados alfabéticamente:");
        for (String ap : apellidos) {
            out.println("- " + ap);
        }
    }

    public void resolverEjercicio9() {
        out.println("\n--- Ejercicio 9: Menor de Cuatro Reales ---");
        double num1 = InputUtils.leerDoubleValidadoBufferedReader("Ingrese el primer número real: ", null, null);
        double num2 = InputUtils.leerDoubleValidadoBufferedReader("Ingrese el segundo número real: ", null, null);
        double num3 = InputUtils.leerDoubleValidadoBufferedReader("Ingrese el tercer número real: ", null, null);
        double num4 = InputUtils.leerDoubleValidadoBufferedReader("Ingrese el cuarto número real: ", null, null);

        double menor = Math.min(Math.min(num1, num2), Math.min(num3, num4));
        out.printf(Locale.US,"El menor de los cuatro números es: %.2f%n", menor);
    }

    public void resolverEjercicio10() {
        out.println("\n--- Ejercicio 10: Par o Impar ---");
        int numero = InputUtils.leerEnteroValidadoBufferedReader("Ingrese un número entero: ", null, null);
        if (numero % 2 == 0) {
            out.println("El número " + numero + " es PAR.");
        } else {
            out.println("El número " + numero + " es IMPAR.");
        }
    }

    public void resolverEjercicio11() {
        out.println("\n--- Ejercicio 11: Divisibilidad Mayor por Menor ---");
        double num1 = InputUtils.leerDoubleValidadoBufferedReader("Ingrese el primer número real: ", null, null);
        double num2 = InputUtils.leerDoubleValidadoBufferedReader("Ingrese el segundo número real: ", null, null);

        if (Math.abs(num1 - num2) < 1e-9) { // Comparación de doubles para igualdad
            if (Math.abs(num1) < 1e-9) { // Ambos son cero
                out.println("Ambos números son cero. La divisibilidad es trivial o indefinida.");
            } else {
                out.println("Los números son iguales y no son cero, por lo tanto, son divisibles entre sí.");
            }
            return;
        }

        double mayor = Math.max(num1, num2);
        double menor = Math.min(num1, num2);

        if (Math.abs(menor) < 1e-9) { // El menor es cero
            out.println("No se puede dividir por cero (el número menor es cero).");
        } else {
            double epsilon = 1e-9;
            if (Math.abs(mayor % menor) < epsilon) {
                out.printf(Locale.US,"El número mayor (%.2f) ES divisible por el número menor (%.2f).%n", mayor, menor);
            } else {
                out.printf(Locale.US,"El número mayor (%.2f) NO ES divisible por el número menor (%.2f). (Resto: %.4f)%n", mayor, menor, mayor % menor);
            }
        }
    }

    public void resolverEjercicio12() {
        out.println("\n--- Ejercicio 12: Signo Zodiacal por Fecha de Nacimiento ---");
        int dia=0, mes=0;
        boolean fechaValida = false;

        while(!fechaValida){
            dia = InputUtils.leerEnteroValidadoBufferedReader("Ingrese el día de nacimiento (1-31): ", 1, 31);
            mes = InputUtils.leerEnteroValidadoBufferedReader("Ingrese el mes de nacimiento (1-12): ", 1, 12);

            if (mes == 2) {
                if (dia <= 29) fechaValida = true;
                else out.println("Febrero no puede tener más de 29 días (simplificado). Intente de nuevo.");
            } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
                if (dia <= 30) fechaValida = true;
                else out.println("Este mes no puede tener más de 30 días. Intente de nuevo.");
            } else {
                if (dia <= 31) fechaValida = true;
            }
        }

        String signo = "Desconocido";
        if ((mes == 3 && dia >= 21) || (mes == 4 && dia <= 19)) signo = "Aries";
        else if ((mes == 4 && dia >= 20) || (mes == 5 && dia <= 20)) signo = "Tauro";
        else if ((mes == 5 && dia >= 21) || (mes == 6 && dia <= 20)) signo = "Géminis";
        else if ((mes == 6 && dia >= 21) || (mes == 7 && dia <= 22)) signo = "Cáncer";
        else if ((mes == 7 && dia >= 23) || (mes == 8 && dia <= 22)) signo = "Leo";
        else if ((mes == 8 && dia >= 23) || (mes == 9 && dia <= 22)) signo = "Virgo";
        else if ((mes == 9 && dia >= 23) || (mes == 10 && dia <= 22)) signo = "Libra";
        else if ((mes == 10 && dia >= 23) || (mes == 11 && dia <= 21)) signo = "Escorpio";
        else if ((mes == 11 && dia >= 22) || (mes == 12 && dia <= 21)) signo = "Sagitario";
        else if ((mes == 12 && dia >= 22) || (mes == 1 && dia <= 19)) signo = "Capricornio";
        else if ((mes == 1 && dia >= 20) || (mes == 2 && dia <= 18)) signo = "Acuario";
        else if ((mes == 2 && dia >= 19 && dia <=29) || (mes == 3 && dia <= 20)) signo = "Piscis";

        if (signo.equals("Desconocido")) {
            out.println("No se pudo determinar el signo para la fecha " + dia + "/" + mes +". Verifique los rangos.");
        } else {
            out.println("Su signo zodiacal para la fecha " + dia + "/" + mes + " es: " + signo);
        }
    }

    public void resolverEjercicio13() {
        out.println("\n--- Ejercicio 13: Apellido Más Largo ---");
        String persona1Completo = InputUtils.leerStringNoVacioBufferedReader("Ingrese nombre y apellido(s) Persona 1: ");
        String persona2Completo = InputUtils.leerStringNoVacioBufferedReader("Ingrese nombre y apellido(s) Persona 2: ");

        String apellido1 = extraerApellido(persona1Completo);
        String apellido2 = extraerApellido(persona2Completo);

        out.println("Procesando '" + persona1Completo + "' -> Apellido: '" + apellido1 + "' (Largo: " + (apellido1.isEmpty() ? 0 : apellido1.length()) + ")");
        out.println("Procesando '" + persona2Completo + "' -> Apellido: '" + apellido2 + "' (Largo: " + (apellido2.isEmpty() ? 0 : apellido2.length()) + ")");

        if (apellido1.isEmpty() && apellido2.isEmpty()) {
            out.println("No se pudieron extraer apellidos de ninguna de las entradas.");
        } else if (apellido1.length() > apellido2.length()) {
            out.println(persona1Completo + " tiene el apellido más largo ('" + apellido1 + "').");
        } else if (apellido2.length() > apellido1.length()) {
            out.println(persona2Completo + " tiene el apellido más largo ('" + apellido2 + "').");
        } else { // Misma longitud (o ambos vacíos y uno tiene longitud 0)
            if (apellido1.isEmpty()) { // Implica que apellido2 también es vacío o longitud 0
                out.println("No se pudieron extraer apellidos válidos para comparar.");
            } else {
                out.println("Ambos apellidos ('" + apellido1 + "' y '" + apellido2 + "') tienen la misma longitud.");
            }
        }
    }

    private String extraerApellido(String nombreCompleto) {
        nombreCompleto = nombreCompleto.trim();
        if (nombreCompleto.isEmpty()) return "";
        int ultimoEspacio = nombreCompleto.lastIndexOf(' ');
        if (ultimoEspacio != -1 && ultimoEspacio < nombreCompleto.length() - 1) {
            return nombreCompleto.substring(ultimoEspacio + 1);
        }
        // Si no hay espacio, considerar toda la cadena como apellido
        if (!nombreCompleto.contains(" ")) {
            return nombreCompleto;
        }
        return ""; // Si termina con espacio o es solo espacios
    }

    public void resolverEjercicio14() {
        out.println("\n--- Ejercicio 14: Tabla de Multiplicar ---");
        int n = InputUtils.leerEnteroValidadoBufferedReader("Ingrese un número natural (>= 0) para su tabla: ", 0, null);
        out.println("Tabla de multiplicar del " + n + ":");
        for (int i = 0; i <= 10; i++) {
            out.printf(Locale.US,"%d x %2d = %d%n", n, i, (n * i));
        }
    }

    public void resolverEjercicio15() {
        out.println("\n--- Ejercicio 15: Número Primo ---");
        int numero = InputUtils.leerEnteroValidadoBufferedReader("Ingrese un número natural (>= 0) para verificar si es primo: ", 0, null);

        if (esPrimo(numero)) {
            out.println("El número " + numero + " ES primo.");
        } else {
            out.println("El número " + numero + " NO ES primo.");
        }
    }

    private boolean esPrimo(int num) {
        if (num <= 1) return false;
        if (num <= 3) return true;
        if (num % 2 == 0 || num % 3 == 0) return false;
        for (int i = 5; i * i <= num; i = i + 6) {
            if (num % i == 0 || num % (i + 2) == 0)
                return false;
        }
        return true;
    }
}