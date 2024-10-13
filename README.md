# restock_notification
재입고 알림 시스템

---
## 1 과제 개요
### 1.1 설명
상품이 재입고 되었을 때, 재입고 알림을 설정한 유저들에게 재입고 알림을 보내줍니다.

### 1.2 비지니스 요구 사항
- 재입고 알림을 전송하기 전, 상품의 재입고 회차를 1 증가 시킨다.
- 실제 서비스에서는 다른 형태로 관리하지만, 과제에서는 직접 관리한다.
- 상품이 재입고 되었을 때, 재입고 알림을 설정한 유저들에게 알림 메시지를 전달해야 한다.
- 재입고 알림은 재입고 알림을 설정한 유저 순서대로 메시지를 전송한다.
- 회차별 재입고 알림을 받은 유저 목록을 저장해야 한다.
- 재입고 알림을 보내던 중 재고가 모두 없어진다면 알림 보내는 것을 중단합니다.
- 재입고 알림 전송의 상태를 DB 에 저장해야 한다.
    - IN_PROGRESS (발송 중)
    - CANCELED_BY_SOLD_OUT (품절에 의한 발송 중단)
    - CANCELED_BY_ERROR (예외에 의한 발송 중단)
    - COMPLETED (완료)


### 1.3 기술적 요구 사항
- 알림 메시지는 1초에 최대 500개의 요청을 보낼 수 있다.
    - 서드 파티 연동을 하진 않고, ProductNotificationHistory 테이블에 데이터를 저장한다.
- Mysql 조회 시, 인덱스를 잘 탈 수 있게 설계해야 합니다.
- 설계해야 할 테이블 목록
    - Product (상품)
    - ProductNotificationHistory (상품별 재입고 알림 히스토리)
    - ProductUserNotification (상품별 재입고 알림을 설정한 유저)
    - ProductUserNotificationHistory (상품 + 유저별 알림 히스토리)
- (Optional) 예외에 의해 알림 메시지 발송이 실패한 경우, manual 하게 상품 재입고 알림 메시지를 다시 보내는 API를 호출한다면 마지막으로 전송 성공한 이후 유저부터 다시 알림 메시지를 보낼 수 있어야 한다.
    - 10번째 유저까지 알림 메시지 전송에 성공했다면, 다음 요청에서 11번째 유저부터 알림 메시지를 전송할 수 있어야 한다.
- 시스템 구조 상 비동기로 처리 되어야 하는 부분은 존재하지 않는다고 가정합니다.


### 2 데이터베이스
https://www.erdcloud.com/d/a7DzpheYFDwmvQHha

![restockNotification.png](..%2FrestockNotification.png)


### 3 시스템 아키텍처
![architechture2.png](..%2Farchitechture2.png)


