package com.arphor.service.impi;

import com.arphor.entity.Category;
import com.arphor.entity.Product;
import com.arphor.dao.ProductDAO;
import com.arphor.service.ProductService;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    
    Product product;

    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDAO.findById(productId).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        return productDAO.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productDAO.save(product);
    }

	@Override
	public List<Product> getProductsByCategoryId(int categoryId) {
		return productDAO.findByCategoryCategoryID(categoryId);
	}

	@Override
	public List<Product> getProductsByParentName(String parentName) {
		return productDAO.findByCategoryParentParentName(parentName);
	}

	@Override
	public List<Product> getRelatedProducts(Integer productId) {
		Product product = getProductById(productId);
        Category category = product.getCategory();
        List<Product> relatedProducts = productDAO.findByCategory(category);
        relatedProducts.removeIf(p -> p.getProductId().equals(productId));
        return relatedProducts;
	}
	
	@Override
	public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
		List<Product> products = productDAO.findByPriceBetween(minPrice, maxPrice);
		return products;
	}

	@Override
	public int getProductByIdfa(int productId) {
		Product product = productDAO.findById(productId).orElse(null);
	    if (product != null) {
	        return product.getProductId();
	    } else {
	        // Handle the case when the product is not found
	        return -1; // Or any other appropriate value indicating the product was not found
	    }
	}

	// Phương thức xóa mềm sản phẩm
	public void deleteProduct(Integer id, boolean softDelete) {
		Product product = null;
		try {
			product = productDAO.findById(id).orElseThrow(NotFoundException::new);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (softDelete) {
			product.setDeleted(1);
		} else {
			productDAO.deleteById(id);
		}

		productDAO.save(product);
	}

	@Override
	public List<Product> getProductsByProductName(String productName) {
		 return productDAO.findByProductNameContaining(productName);
	}
}
