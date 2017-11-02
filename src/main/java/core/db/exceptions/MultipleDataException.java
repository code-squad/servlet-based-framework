package core.db.exceptions;

public class MultipleDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public static final String STANDARD_MULTI_DATA_ERR_MSG = "한 개 이상의 데이터가 검색되었습니다.";

	public MultipleDataException(String msg) {
		super(msg);
	}
}
