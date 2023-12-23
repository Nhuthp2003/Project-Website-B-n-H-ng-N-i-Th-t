package com.arphor.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arphor.entity.Category;
import com.arphor.entity.Product;
import com.arphor.service.CategoryService;
import com.arphor.service.ProductService;

@Controller
@RequestMapping
public class ExcelController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	private List<Product> getProductData() {
		return productService.getAllProducts();
	}

	private short colorIndex = (short) (IndexedColors.LIGHT_GREEN.getIndex() + 1);

	private short getNextColorIndex() {
		return colorIndex++;
	}

	@GetMapping("/export-excel-product")
	public ResponseEntity<byte[]> exportExcel() throws Exception {
		Workbook workbook = new XSSFWorkbook();

		Sheet sheet = workbook.createSheet("Product Data");

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerStyle.setFont(headerFont);

		Row headerRow = sheet.createRow(0);
		String[] headers = { "Product", "Category", "Stock", "Price" };
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(headerStyle);
		}

		List<Product> products = getProductData();
		int rowNum = 1;

		Map<String, CellStyle> categoryStyles = new HashMap<>();

		for (Product product : products) {
			Row dataRow = sheet.createRow(rowNum++);
			dataRow.createCell(0).setCellValue(product.getProductName());
			dataRow.createCell(1).setCellValue(product.getCategory().getCategoryName());
			dataRow.createCell(2).setCellValue(product.getStock());
			dataRow.createCell(3).setCellValue(product.getPrice());
			if (product.getDeleted() == 1) {
				dataRow.createCell(4).setCellValue("Đã xóa");
			}

//			String categoryName = product.getCategory().getCategoryName();
//			CellStyle categoryStyle = categoryStyles.get(categoryName);
//			if (categoryStyle == null) {
//				categoryStyle = workbook.createCellStyle();
//				categoryStyle.setFillForegroundColor(getNextColorIndex());
//				categoryStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//				categoryStyles.put(categoryName, categoryStyle);
//			}
//
//			// Áp dụng định dạng và màu sắc cho toàn bộ dòng dữ liệu
//			for (int i = 0; i < headers.length; i++) {
//				Cell cell = dataRow.getCell(i);
//				cell.setCellStyle(categoryStyle);
//			}
		}

		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn(i);
		}

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		header.setContentDispositionFormData("attachment", "product_data.xlsx");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);

		return ResponseEntity.ok().headers(header).body(outputStream.toByteArray());
	}

	@GetMapping("/export-excel-category")
	public ResponseEntity<byte[]> exportExcelCategory() throws Exception {
		Workbook workbook = new XSSFWorkbook();

		Sheet sheet = workbook.createSheet("Category Data");

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerStyle.setFont(headerFont);

		Row headerRow = sheet.createRow(0);
		String[] headers = { "CategoryID", "CategoryName", "Image", "Description", "Quantity", "Parent" };
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(headerStyle);
		}

		List<Category> categories = getCategoryData();
		List<Product> products = getProductData();
		int rowNum = 1;
		for (Category category : categories) {
			int productCount = 0;
			for (Product product : products) {
				if (product.getCategory().getCategoryName().equals(category.getCategoryName())) {
					productCount++;
				}
			}
			Row dataRow = sheet.createRow(rowNum++);
			dataRow.createCell(0).setCellValue(category.getCategoryID());
			dataRow.createCell(1).setCellValue(category.getCategoryName());
			dataRow.createCell(2).setCellValue(category.getImage());
			dataRow.createCell(3).setCellValue(category.getDescription());
			dataRow.createCell(4).setCellValue(productCount);
			dataRow.createCell(5).setCellValue(category.getParent().getParentName());
		}

		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn(i);
		}

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		header.setContentDispositionFormData("attachment", "category_data.xlsx");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);

		return ResponseEntity.ok().headers(header).body(outputStream.toByteArray());
	}

	private List<Category> getCategoryData() {
		// TODO Auto-generated method stub
		return categoryService.getAllCategories();
	}

}
