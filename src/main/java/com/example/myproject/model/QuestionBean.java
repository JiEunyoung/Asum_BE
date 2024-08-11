package com.example.myproject.model;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class QuestionBean {

   public Map<String, String[]> cleaning = getCleaning();
   public Map<String, String[]> tramslation = getTranslation(); 
   public Map<String, String[]> document = getDocument();
   public Map<String, String[]> interior = getInterior();
   public Map<String, String[]> development = getDevelopment();
   public Map<String, String[]> certifaction_exam = getCertifaction_exam();
   public Map<String, String[]> pet = getPet();
   public Map<String, String[]> application = getAppliance(); 

   public static Map<String, String[]> getCleaning() {
      return new LinkedHashMap<String, String[]>() {
         {
            put("1. 원하는 서비스를 선택해주세요", new String[] { "건물 청소 해주세요", "가구 청소 해주세요", "전자제품 청소 해주세요", "기타" });
            put("2. 건물의 유형을 알려주세요", new String[] { "아파트 청소해주세요", "빌라 청소해주세요", "단독주택 청소해주세요", "오피스텔 청소해주세요", "업소/매장/상가 청소해주세요", "공장/창고 청소해주세요", "기타" });
            put("3. 서비스가 필요한 장소의 크기나 면적을 알려주세요", new String[] { "10평 미만 입니다", "20평대 입니다", "30평대 입니다", "40평대 입니다", "기타" });
            put("4. 특별한 환경이나 제약사항이 있다면 알려주세요", new String[] { "작성", "없어요" });
            put("5. 서비스 희망일을 알려주세요 (오는 날)", new String[] { "매칭 후 협의할게요", "최대한 빨리 해주세요", "일주일 이내로 할게요","기타" });
         }
      };
   }
   
   public static Map<String, String[]> getTranslation() {
      return new LinkedHashMap<String, String[]>() {
         {
             put("1. 원하는 번역을 선택해주세요", new String[]{"외국어 -> 한국어 번역해주세요", "한국어 -> 외국어 번역해주세요", "외국어(1) => 외국어(2) 번역해주세요", "기타"});
              put("2. 번역할 분야를 선택해주세요", new String[]{"일반 번역(이력서/자소서/에세이 등)입니다", "비즈니스 번역 입니다", "학술/논문 번역 입니다", "전문분야(IT/인문/사회/법률/의학/과학 등)번역 입니다", "기타"});
              put("3. 번역할 분량을 알려주세요(A4 기준)", new String[]{"분량: 1장을 원해요", "분량: 10장 이하를 원해요", "분량: 30장 이하를 원해요", "분량: 50장 이하를 원해요", "기타"});
              put("4. 서비스 마감 희망일을 알려주세요", new String[]{"매칭 후 협의할게요", "최대한 빨리 해주세요", "일주일 이내로 할게요", "기타"});
          }
      };
   } 
   
   public static Map<String, String[]> getDocument() {
      return new LinkedHashMap<String, String[]>() {
         {
             put("1. 분량은 얼마나 되나요?", new String[]{"분량: 1장", "분량: 10장 이하", "분량: 30장 이하", "분량: 50장 이하", "기타"});
              put("2. 예산이 있나요?", new String[]{"예산: 5만원 미만", "예산: 10만원 미만", "예산: 15만원 미만", "예산: 20만원 미만", "고수와 상담 후 결정할게요", "기타"});
              put("3. 완료 희망일이 언제인가요?", new String[]{"매칭 후 협의할게요", "최대한 빨리 해주세요", "일주일 이내로 할게요", "기타"});
         }
      };
   } 
   
   public static Map<String, String[]> getInterior() {
      return new LinkedHashMap<String, String[]>() {
         {
             put("1. 서비스 하기 전 상황을 선택해주세요", new String[]{"짐 있는 상태입니다", "비어있는 상태 입니다"});
              put("2. 예산을 선택해주세요", new String[]{"상담 후 결정할게요", "예산: 100만 원 미만", "예산: 100만 원대", "200만 원대", "예산: 300만 원대", "기타"});
              put("3. 서비스 희망일을 선택해주세요", new String[]{"매칭 후 협의할게요", "최대한 빨리 해주세요", "일주일 이내로 할게요", "기타"});
          }
      };
   } 
   
   public static Map<String, String[]> getDevelopment() {
      return new LinkedHashMap<String, String[]>() {
         {
             put("1. 의뢰하려는 제작 상태를 선택해주세요", new String[]{"신규 제작 상태입니다", "리뉴얼 상태입니다"});
              put("2. 기획은 어느정도 되어 있나요", new String[]{"아이디어만 있어요", "필요 내용 정리되어 있어요", "상세한 기획 문서 있어요", "레이아웃, 디자인 구성 완료되어 있어요", "기타"});
              put("3. 원하는 숙련도의 개발자가 있으신가요", new String[]{"주니어(3년 이하) 입니다", "미드(4~6년) 입니다", "시니어(6년 이상) 입니다", "무관해요", "기타"});
              put("4. 서비스 희망일을 선택해주세요", new String[]{"매칭 후 협의할게요", "최대한 빨리 해주세요", "일주일 이내로 할게요", "기타"});
              put("5. 어떻게 진행하기 원하시나요", new String[]{"온라인 진행 원해요", "제가 있는 곳으로 와주세요", "일류가 있는 곳으로 갈게요", "어떤 방식이든 상관없어요"});
          }
      };
   }  
   
   public static Map<String, String[]> getCertifaction_exam() {
      return new LinkedHashMap<String, String[]>() {
         {
            put("1. 어떤 레슨 형태를 원하시나요?", new String[]{"개인 레슨", "그룹 레슨", "온라인/화상 레슨", "학원", "무관", "기타"});
              put("2. 희망하는 레슨 시간대는 언제인가요?", new String[]{"이른 오전(9시 이전)", "오전 (9~12시)", "오후 (12~3시)", "늦은 오후 (3~6시)", "저녁 (6~9시)", "늦은 저녁 (9시 이후)"});
              put("3. 언제부터 시작하기 원하시나요?", new String[]{"매칭 후 협의할게요", "최대한 빨리 해주세요", "일주일 이내로 할게요",  "기타"});
           }
      };
   } 
   
   public static Map<String, String[]> getPet() {
      return new LinkedHashMap<String, String[]>() {
         {
            put("반려동물 종류", new String[]{"강아지입니다", "고양이입니다", "기타"});
              put("몇마리", new String[]{"1마리입니다", "2마리입니다", "3마리입니다", "이상"});
              put("반려동물 크기", new String[]{"크기는 5kg 미만", "크기는 10kg 미만", "크기는 15kg 미만", "크기는 20kg 미만", "이상"});
              put("날짜", new String[]{"매칭 후 협의할게요", "최대한 빨리 해주세요", "일주일 이내로 할게요", "기타"});
         }
      };
   } 
   public static Map<String, String[]> getAppliance() {
      return new LinkedHashMap<String, String[]>() {
         {
            put("증상",  new String[]{"작성"});
              put("원하는 문의", new String[]{"설치해주세요", "수리해주세요"});
              put("사용할 장소", new String[]{"아파트 입니다, 주택 입니다, 빌라 입니다", "물류창고 입니다", "사무공간 입니다"});
              put("날짜", new String[]{"매칭 후 협의할게요", "최대한 빨리 해주세요", "일주일 이내로 할게요", "기타"});
         }
      };
   } 
  
}