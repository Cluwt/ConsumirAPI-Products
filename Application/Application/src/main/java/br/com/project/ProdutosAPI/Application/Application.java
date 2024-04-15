package br.com.project.ProdutosAPI.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import br.com.project.ProdutosAPI.Application.Services.ProductService;
import br.com.project.ProdutosAPI.Application.Models.ProdutoModel;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		// Início do Sistema para consumir API de Produtos.
		ProductService productService = new ProductService();
		try {
			// Tentar pegar a lista de Produtos em Promoção:
			List<ProdutoModel> promotionProducts = productService.getPromocao();
			// Para cada produto em promoção:
			for (ProdutoModel product : promotionProducts) {
				// Imprimir o nome e o preço do produto
				System.out.println(product.getTitle().toUpperCase() + " - R$" + product.getPrice());
			}
		} catch (IOException e) {
			// Caso aconteça algum erro:
			System.err.println("Erro ao consumir a API: " + e.getMessage());
		}
	}
}
