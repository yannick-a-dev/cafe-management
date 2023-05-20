//package com.cafemanagementproject.cafemanagementproject.service.impl;
//
//import java.io.FileOutputStream;
//import java.util.Map;
//import java.util.stream.Stream;
//import org.json.simple.JSONArray;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import com.cafemanagementproject.cafemanagementproject.constants.CafeConstants;
//import com.cafemanagementproject.cafemanagementproject.jwt.JWTAuthenticationFilter;
//import com.cafemanagementproject.cafemanagementproject.models.Bill;
//import com.cafemanagementproject.cafemanagementproject.repository.BillRepo;
//import com.cafemanagementproject.cafemanagementproject.service.BillService;
//import com.cafemanagementproject.cafemanagementproject.utils.CafeUtils;
//import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
//import lombok.extern.slf4j.Slf4j;
//import com.itextpdf.*;
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//
//@Service
//@Slf4j
//public class BillServiceImpl implements BillService {
//
//	@Autowired
//	private JWTAuthenticationFilter jwtAuthenticationFilter;
//
//	@Autowired
//	private BillRepo billRepo;
//
//	@Autowired
//	@Override
//	public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
//		log.info("Inside generateReport");
//		try {
//			String fileName;
//			if (validateRequestMap(requestMap)) {
//				if (requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate")) {
//					fileName = (String) requestMap.get("uuid");
//				} else {
//					fileName = CafeUtils.getUUID();
//					requestMap.put("uuid", fileName);
//					insertBill(requestMap);
//				}
//
//				String data = "Name: " + requestMap.get("name") + "\n" + "Contact Number: "
//						+ requestMap.get("contactNumber") + "\n" + "Email: " + requestMap.get("email") + "\n"
//						+ "Payment Method: " + requestMap.get("paymentMethod");
//
//				Document document = new Document();
//				PdfWriter.getInstance(document,
//						new FileOutputStream(CafeConstants.STORE_LOCATION + "\\" + fileName + ".pdf"));
//
//				document.open();
//				setRectangleInPdf(document);
//
//				Paragraph chunk = new Paragraph("Cafe Management System", getFont("Header"));
//				chunk.setAlignment(Element.ALIGN_CENTER);
//				document.add(chunk);
//
//				Paragraph paragraph = new Paragraph(data + "\n \n", getFont("Data"));
//				document.add(paragraph);
//
//				PdfPTable table = new PdfPTable(5);
//				table.setWidthPercentage(100);
//				addTableHeader(table);
//			}
//			return CafeUtils.getResponseEntity("Required data not found.", HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//
//	private boolean validateRequestMap(Map<String, Object> requestMap) {
//
//		return requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
//				&& requestMap.containsKey("email") && requestMap.containsKey("paymentMethod")
//				&& requestMap.containsKey("productDetails") && requestMap.containsKey("totalAmount");
//	}
//
//	private void insertBill(Map<String, Object> requestMap) {
//		try {
//			Bill bill = new Bill();
//			bill.setUuid((String) requestMap.get("uuid"));
//			bill.setName((String) requestMap.get("name"));
//			bill.setEmail((String) requestMap.get("email"));
//			bill.setContactNumber((String) requestMap.get("contactNumber"));
//			bill.setPaymentMethod((String) requestMap.get("paymentMethod"));
//			bill.setTotal(Integer.parseInt((String) requestMap.get("totalAmount")));
//			bill.setProductDetail((String) requestMap.get("productDetails"));
//			bill.setCreatedBy(jwtAuthenticationFilter.getCurrentUser());
//			billRepo.save(bill);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void setRectangleInPdf(Document document) throws DocumentException {
//		log.info("Inside setRectangleInPdf");
//		Rectangle rect = new Rectangle(577, 825, 18, 15);
//		rect.enableBorderSide(1);
//		rect.enableBorderSide(2);
//		rect.enableBorderSide(40);
//		rect.enableBorderSide(8);
//		rect.setBackgroundColor(BaseColor.BLACK);
//		rect.setBorderWidth(1);
//		document.add(rect);
//	}
//
//	private Font getFont(String type) {
//		log.info("Inside getFont");
//		switch (type) {
//		case "Header":
//			Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
//			headerFont.setStyle(Font.BOLD);
//			return headerFont;
//		case "Data":
//			Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
//			dataFont.setStyle(Font.BOLD);
//			return dataFont;
//		default:
//			return new Font();
//		}
//	}
//
//	private void addTableHeader(PdfPTable table) {
//		log.info("Inside addTableHeader");
//		Stream.of("Name", "Category", "Quantity", "Price", "Sub Total").forEach(columnTitle -> {
//			PdfPCell header = new PdfPCell();
//			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
//			header.setBorderWidth(2);
//			header.setPhrase(new Phrase(columnTitle));
//			header.setBackgroundColor(BaseColor.YELLOW);
//			header.setHorizontalAlignment(Element.ALIGN_CENTER);
//			header.setVerticalAlignment(Element.ALIGN_CENTER);
//			table.addCell(header);
//		});
//	}
//
//}
