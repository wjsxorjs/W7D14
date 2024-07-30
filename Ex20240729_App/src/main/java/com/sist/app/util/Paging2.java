package com.sist.app.util;

public class Paging2 {
	int nowPage = 1; // 현재 페이지
	int numPerPage = 10; // 한 페이지 당 표현할 게시물 수 
	int totalRecord; // 총 게시물 수
	int pagePerBlock = 5; // 한 블럭 당 표현할 페이지 수
	int totalPage; // 총 페이지 수
	int begin; // 현재 페이지 값에 따라 bbs_t테이블에서 가져올 게시물의 시작 행번호
	int end; // 현재 페이지 값에 따라 bbs_t테이블에서 가져올 게시물의 끝 행번호
	int startPage; // 한 블럭의 시작페이지 값
	int endPage; // 한 블럭의 끝페이지 값
	
	private boolean isPrePage; // 이전 기능 가능여부 (true일 때 이전기능 활성화)
	private boolean isNextPage; // 이전 기능 가능여부 (true일 때 다음기능 활성화)
	
	
	// JSP에서 표현할 페이징 HTML코드를 생성하여 저장할 곳
	private StringBuffer sb;
	
	public Paging2() {}
	
	public Paging2(int numPerPage, int pagePerBlock, int totalRecord, int nowPage, String bname) {
		// 인자인 지역변수를 멤버변수에 저장
		this.numPerPage = numPerPage;
		this.pagePerBlock = pagePerBlock;
		this.setTotalRecord(totalRecord);
		this.setNowPage(nowPage);
		
		// 이전/다음 기능 초기화
		if(startPage > 1) {
			isPrePage = true;
		} else {
			isPrePage = false;
		}
		if(endPage < totalPage) {
			isNextPage = true;
		} else {
			isNextPage = false;
		}
		
		// 페이징 기법에 필요한 데이터를 모두 저장했으므로
		// 페이징 기법에 사용할 HTML코드를 작성하여 StringBuffer에 저장해야 한다.
		sb = new StringBuffer("<ol class='paging'>");
		
		// 이전 기능
		if(isPrePage) {
			sb.append("<li><a href='list?bname=");
			sb.append(bname);
			sb.append("&cPage=");
			sb.append(nowPage - pagePerBlock);
			sb.append("'>&lt;</a></li>");
		} else {
			sb.append("<li class='disable'>&lt;</li>");
		}
		for(int i=startPage; i<=endPage;i++) {
			// i가 현재페이지 값과 같을 때는
			// a태그를 지정하지 않고 숫자만 출력하자!
			if(i == nowPage) {
				sb.append("<li class='now'>");
				sb.append(i);
				sb.append("</li>");
			} else {
				sb.append("<li><a href='list?bname=");
				sb.append(bname);
				sb.append("&cPage=");
				sb.append(i);
				sb.append("'>");
				sb.append(i);
				sb.append("</a></li>");
			}
		} // for문 끝
		
		// 다음 기능
		if(isNextPage) {
			sb.append("<li><a href='list?bname=");
			sb.append(bname);
			sb.append("&cPage=");
			sb.append(nowPage + pagePerBlock);
			sb.append("'>&gt;</a></li>");
		} else {
			sb.append("<li class='disable'>&gt;</li>");
		}
		
		sb.append("</ol>");
		
				
	}

	public void setNowPage(int nowPage) {
		// 현재 페이지 값이 변경되고 있으니 begin과 end 그리고
		// startPage와 endPage값을 구한다.
		// 먼저 무슨 페이지든 간에 총 페이지 값을 넘어서는 안된다.
		if(nowPage > totalPage) {
			nowPage = totalPage;
		}

		this.nowPage = nowPage;
		
		// 각 페이지의 시작레코드의 행 번호와 마지막레코드의 행번호 지정.
		// 현재 페이지 값 1 : begin = 1 || end = 10
		// 현재 페이지 값 2 : begin = 11 || end = 20
		begin = (nowPage-1)*numPerPage+1;
		end = nowPage * numPerPage;
		
		// 현재 페이지 값에 의해 블럭의 시작페이지, 끝페이지 값 구하기
		startPage = (int) ((nowPage-1)/pagePerBlock)*pagePerBlock+1;
		endPage = startPage + pagePerBlock - 1;
		
		// 마지막 페이지 값이 총 페이지 값을 넘어서는 안된다.
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		
		// 총 게시물의 수가 변경될 때 총 페이지 값도 변경
//		totalPage = totalRecord / numPerPage;
//		if((totalRecord%numPerPage) != 0) {
//			totalPage++;
//		}
		totalPage =  (int) Math.ceil((double)totalRecord / numPerPage);
		
		
		
	}

	public void setPagePerBlock(int pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	
	
	
	public int getNowPage() {
		return nowPage;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public int getPagePerBlock() {
		return pagePerBlock;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}
	
	public StringBuffer getSb() {
		return sb;
	}

	
}
