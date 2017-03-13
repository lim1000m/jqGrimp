package c.e.g.exception;

import java.util.logging.Logger;

/**
 * @File Name : GrimpExceptionHandler.java
 * @Project Name : grimp-component
 * @package Name : c.e.g.exception
 * @create Date : 2016. 3. 23.
 * @explain : Exception Handler
 *            발생하는 모든 익셉션 종류를 모아놓고 처리 
 *            try catch 구문이 광범위 해서 라인까지 찾긴 힘듬 
 * @made by : "GOEDOKID"
 */
@SuppressWarnings("serial")
public class GrimpExceptionHandler extends Exception{

	private static final Logger logger = Logger.getLogger(GrimpExceptionHandler.class.getName());
	
	public enum GrimpError {CIE, CNFE, FNPE, IAE, NPE, NSFE, JPE, NSFEV, IARE, SE, PE}
	
	public GrimpExceptionHandler() {
		super("Error occurred while grimp axis grid assembler working!!");
	}
	
	public GrimpExceptionHandler(GrimpError ge, Exception e) {
		
		super("Error occurred while grimp axis grid assembler working!!");
		
		StackTraceElement[] ste = e.getStackTrace();
		
		logger.warning("[GRIMP ERROR INFO] "+ste[0].getClassName()+"."+ste[0].getMethodName()+"."+ste[0].getLineNumber());
		
		if(ge.equals(GrimpError.NPE)) {
			logger.warning("Annotation setting not exist.");
			logger.warning("Reason : Did you really define the annotation in class in function package ?");
		} else if(ge.equals(GrimpError.NSFE)) {
			logger.warning("Target field is not exist.");
			logger.warning("Reason : Cannot find the target field the class in Function package.");
		} else if(ge.equals(GrimpError.CNFE)) {
			logger.warning("Could not find Class in function Package or Failed to instance the class.");
			logger.warning("Reason : Check it out Class in Function package is exist and same class name with you choose.");
		} else if(ge.equals(GrimpError.FNPE)) {
			logger.warning("Annotation setting not exist.");
			logger.warning("Reason : Did you really define the annotation in class in function package ?");
		} else if (ge.equals(GrimpError.CIE)) {
			logger.warning("Could not find Class in function Package or Failed to instance the class.");
			logger.warning("Reason : Check it out Class in Function package is exist and same class name with you choose.");
		} else if(ge.equals(GrimpError.IAE)) {
			logger.warning("Not Well form Annotation and Definition variable.");
		} else if(ge.equals(GrimpError.PE)) {
			logger.warning("Error occured while converting String to date. Check Date Exp is Valid.");
		}
	}
}
