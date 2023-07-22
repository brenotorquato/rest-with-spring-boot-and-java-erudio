package br.com.torquato;

import org.springframework.web.bind.annotation.RestController;

import br.com.torquato.exceptions.UnsupportedMathOperationException;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

enum Operations {
	sum, sub, mult, div, med, sqrt;
}

@RestController
public class MathController {
	
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double sum(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
		) throws Exception {
		
		return prepareOperation(Operations.sum, numberOne, numberTwo);
		
	}
	
	@RequestMapping(value = "/sub/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double sub(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
		) throws Exception {

		return prepareOperation(Operations.sub, numberOne, numberTwo);
	}
	
	@RequestMapping(value = "/mult/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double mult(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
		) throws Exception {

		return prepareOperation(Operations.mult, numberOne, numberTwo);
	}
	
	@RequestMapping(value = "/div/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double div(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
		) throws Exception {

		return prepareOperation(Operations.div, numberOne, numberTwo);
	}

	@RequestMapping(value = "/med/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double med(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
		) throws Exception {

		return prepareOperation(Operations.med, numberOne, numberTwo);
	}
	
	@RequestMapping(value = "/sqrt/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double sqrt(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
		) throws Exception {

		return prepareOperation(Operations.sqrt, numberOne, numberTwo);
	}

	private Double prepareOperation(Operations operation, String numberOne, String numberTwo) throws Exception {
		
		Boolean isNumeric = false;

		try {
			isNumeric = checkIsNumericsValues(numberOne, numberTwo);
		} catch (Exception exp) {
			throw exp;
		}

		return isNumeric ? executeOperation(operation, numberOne, numberTwo) : 0D;

	}
	
	private Double executeOperation(Operations operation, String numberOne, String numberTwo) throws Exception {
			switch (operation) {
				case sum: {
					return convertToDouble(numberOne) + convertToDouble(numberTwo);
				}
				case sub: {
					return convertToDouble(numberOne) - convertToDouble(numberTwo);
				}
				case mult: {
					return convertToDouble(numberOne) * convertToDouble(numberTwo);
				}
				case div: {
					return convertToDouble(numberOne) / convertToDouble(numberTwo);
				}
				case med: {
					return (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2;
				}
				case sqrt: {
					return Math.pow(convertToDouble(numberOne), convertToDouble(numberTwo));
				}
			
				default:
					throw new IllegalArgumentException("Unexpected value: " + operation);
		}
	}

	private Boolean checkIsNumericsValues(String numberOne, String numberTwo) throws Exception {
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value");
		}
		return true;
	}

	private Double convertToDouble(String strNumber) {
		if (strNumber == null) return 0D;
		String number = strNumber.replaceAll(",", ".");
		if (isNumeric(number)) return Double.parseDouble(number);
		return 0D;
	}

	private boolean isNumeric(String strNumber) {
		if (strNumber == null) return false;
		String number = strNumber.replaceAll(",", ".");
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}

}
