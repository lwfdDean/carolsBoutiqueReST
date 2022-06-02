/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.za.carolsBoutique.Sale.service;

import co.za.carolsBoutique.codeGenerator.CodeGenerator;

/**
 *
 * @author wille
 */
public class SaleIdGenerator implements CodeGenerator{

	@Override
	public String generateId(String mainSource, String subSource, boolean alphaNumeric) {
		StringBuilder sb = new StringBuilder();
		if (alphaNumeric) {
			int num = (int) (Math.random() * 9000 + 1000);
			for (int i = 0; i < 6; i++) {
				if (i % 2 != 0) {
					char a = mainSource.charAt((int) (Math.random() * mainSource.length()));
					if (!Character.isLetter(a)) {
						i--;
						continue;
					}
					sb.append(a);
				}
				if (i % 2 == 0) {
					char a = subSource.charAt((int) (Math.random() * subSource.length()));
					if (!Character.isLetter(a)) {
						i--;
						continue;
					}
					if (i == 2) {
						sb.append(num);
					}
					sb.append(a);
				}
			}
		} else {
			for (int i = 0; i < 10; i++) {
				if (i % 2 != 0) {
					char a = mainSource.charAt((int) (Math.random() * mainSource.length()));
					if (!Character.isLetter(a)) {
						i--;
						continue;
					}
					sb.append(a);
				}
				if (i % 2 == 0) {
					char a = subSource.charAt((int) (Math.random() * subSource.length()));
					if (!Character.isLetter(a)) {
						i--;
						continue;
					}
					sb.append(a);
				}
			}
		}
		return sb.toString();
	}

	@Override
	public String generateId(String source, boolean alphaNumeric) {
		StringBuilder sb = new StringBuilder();
		if (alphaNumeric) {
			int num = (int) (Math.random() * 9000 + 1000);
			for (int i = 0; i < 6; i++) {
				char a = source.charAt((int) (Math.random() * source.length()));
				if (!Character.isLetter(a)) {
					i--;
					continue;
				}
				if (i == 2) {
					sb.append(num);
				}
				sb.append(a);
			}
		} else {
			for (int i = 0; i < 10; i++) {
				char a = source.charAt((int) (Math.random() * source.length()));
				if (!Character.isLetter(a)) {
					i--;
					continue;
				}
				sb.append(a);
			}
		}
		return sb.toString();
	}
	
}
