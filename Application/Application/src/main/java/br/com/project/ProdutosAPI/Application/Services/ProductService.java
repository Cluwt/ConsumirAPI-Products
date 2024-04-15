package br.com.project.ProdutosAPI.Application.Services;

import br.com.project.ProdutosAPI.Application.Models.ProdutoModel;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    public List<ProdutoModel> getPromocao() throws IOException {
        // Lista para armazenar os produtos em promoção
        List<ProdutoModel> promotionProducts = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // Fazendo a requisição à API
        HttpGet request = new HttpGet("https://api.escuelajs.co/api/v1/products/");

        try (CloseableHttpResponse response = httpClient.execute(request);
             BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {

            StringBuilder result = new StringBuilder();
            String line;
            // Lê cada linha da resposta e adiciona ao StringBuilder
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            try {
                // Converte a resposta em um JSONArray
                JSONArray productsArray = new JSONArray(result.toString());
                // Itera sobre os produtos do JSONArray
                for (int i = 0; i < productsArray.length(); i++) {
                    JSONObject productObject = productsArray.getJSONObject(i);
                    // Obtém o preço do produto
                    double price = productObject.getDouble("price");
                    // Verifica se o preço é menor que 30
                    if (price < 30) {
                        // Se for, adiciona o produto à lista de produtos em promoção
                        String title = productObject.getString("title");
                        ProdutoModel product = new ProdutoModel(title, price);
                        promotionProducts.add(product);
                    }
                }
            } catch (Exception e) {
                System.err.println("Erro ao analisar o JSON da resposta da API: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Erro ao fazer a req HTTP: " + e.getMessage());
        }

        return promotionProducts;
    }
}
