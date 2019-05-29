package com.cafe24.mysite.util;

public class Paging {

	private final int pageCount = 5;
	private int blockStartNum = 0;
	private int blockLastNum = 0;
	private int startPageNum = 0;
	private int lastPageNum = 0;
	private int curPageNum = 0;
	
	
	public int getBlockStartNum() {
		return blockStartNum;
	}
	
//	public void setBlockStartNum(int blockStartNum) {
//		this.blockStartNum = blockStartNum;
//	}

	public int getCurPageNum() {
		return curPageNum;
	}

	public int getBlockLastNum() {
		return blockLastNum;
	}

//	public void setBlockLastNum(int blockLastNum) {
//		this.blockLastNum = blockLastNum;
//	}

	public int getStartPageNum() {
		return startPageNum;
	}

	public int getLastPageNum() {
		return lastPageNum;
	}

//	public void setLastPageNum(int lastPageNum) {
//		this.lastPageNum = lastPageNum;
//	}
	
	// 총 페이지의 마지막 번호
	public void makeLastPageNum(Long totalBoardCount ,int showBoardNum) {
		
		Long total = totalBoardCount;

		if (total % showBoardNum == 0) {
			// 총 게시글숫자에서 한페이지에 보여줄 숫자로 나눈 값이 0일때, 즉 100개의 게시물은 총 10개 페이지로 나타낼 수 있다.>>
			// page블록이 2개 필요 >> < 1 2 3 4 5 > < 6 7 8 9 10 >
			// 예를들어 80개의 게시물이있다면 10개로 나눴을때 8.0이 되고 8개의 페이지로 나타낼 수 있다.
			lastPageNum = (int) Math.floor(total / showBoardNum);
		} else {
			// 만약 총 게시글의 숫자가 99 일때 한페이지에 보여줄 숫자인 10으로 나눴을때 9.9가 나온다 이때는 9페이지로 나타낼 수 없기 때문에
			// +1 을하여 10페이지에 나타낸다.
			// 예를들어 85개의 게이물이 있다면 10개로 나눴을때 8.5가 되고 8개의 페이지는 부족하기때문에 마지막 페이지는 9가 된다.
			lastPageNum = (int) Math.floor(total / showBoardNum) + 1;
		}
	}
	
	// block을 생성
	// 현재 페이지가 속한 block의 시작 번호, 끝 번호를 계산
	public void makeBlock(int curPageNum) {
		int blockNum = 0;
		
		// ex 현재페이지가 8이면 (8-1)/5 = 1.4 정도가 되는데 소수점을 버리면 1이된다
		blockNum = (int) Math.floor((curPageNum - 1) / pageCount);
		// < 1 2 3 4 5 > < 6 7 8 9 10 > 등이 하나의 블록이라고 했을때 블록의 시작번호는 위에서 구한 (1 * 5)+1 = 6이
		// 시작 블록번호가 된다
		// 예를들어 현재페이지가 3이라면 (3-1)/5 = 0.6 에서 소숫점을 버리면 0이되고 (0 * 5)+1 = 1이 시작 블록번호가 된다.
		blockStartNum = (blockNum * pageCount) + 1;
		// 시작블록번호를 구한다음 (페이지카운트 5) -1 을 한 숫자에 더한다.
		// 그러면 시작블록이 위에서구한 6 + (5-1) = 10이 끝 블록번호가 된다
		// 예를들어 시작블록이 1이면 1+(5-1) = 5가 끝 블록번호가된다.
		blockLastNum = blockStartNum + (pageCount - 1);
		
		blockLastNum = blockLastNum < lastPageNum ? blockLastNum : lastPageNum;
	}
	
	public void makeStartPageNum(int showBoardNum) {
		startPageNum = (curPageNum -1) *showBoardNum;
	}
	
	public void pagingSetting(Long totalBoardCount, int showBoardNum, int curPageNum) {
		makeLastPageNum(totalBoardCount, showBoardNum);
		this.curPageNum = curPageNum;
		makeBlock(curPageNum);
		makeStartPageNum(showBoardNum);
		
	}
}
