import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class Principal {
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        String apiKey = "86b52623d1683c90ce5e8538";
        int opcion = 0;

        while (opcion != 9) {
            System.out.println("******************************************");
            System.out.println("Sea bienvenido/a al Conversor de Moneda");
            System.out.println("1) Dólar => Sol Peruano");
            System.out.println("2) Sol Peruano => Dólar");
            System.out.println("3) Dólar => Peso argentino");
            System.out.println("4) Peso argentino => Dólar");
            System.out.println("5) Dólar => Real brasileño");
            System.out.println("6) Real brasileño => Dólar");
            System.out.println("7) Dólar => Peso colombiano");
            System.out.println("8) Peso colombiano => Dólar");
            System.out.println("9) Salir");
            System.out.println("Elija una opción válida: ");
            System.out.println("******************************************");

            opcion = lectura.nextInt();

            if (opcion == 9) break;

            if (opcion < 1 || opcion > 8) {
                System.out.println("Opción no válida. Intente de nuevo.");
                continue;
            }

            System.out.print("Ingrese el valor que desea convertir: ");
            double valor = lectura.nextDouble();

            String base = "USD", target = "PEN";

            if (opcion == 2) { base = "PEN"; target = "USD"; }
            else if (opcion == 3) { base = "USD"; target = "ARS"; }
            else if (opcion == 4) { base = "ARS"; target = "USD"; }
            else if (opcion == 5) { base = "USD"; target = "BRL"; }
            else if (opcion == 6) { base = "BRL"; target = "USD"; }
            else if (opcion == 7) { base = "USD"; target = "COP"; }
            else if (opcion == 8) { base = "COP"; target = "USD"; }

            try {
                String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + base + "/" + target + "/" + valor;

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
                double resultado = jsonObject.get("conversion_result").getAsDouble();

                System.out.println("El valor " + valor + " [" + base + "] corresponde al valor final de =>>> " + String.format("%.2f", resultado) + " [" + target + "]");
            } catch (Exception e) {
                System.out.println("Error: No se pudo conectar con la API.");
            }
        }
        System.out.println("¡Programa finalizado con éxito!");
    }
}