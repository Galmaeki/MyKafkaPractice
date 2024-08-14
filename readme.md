## Spring 프로젝트에서 Kafka 사용하기
### Config
- Producer 설정 파일 작성 후 빈 추가
- Consumer 설정 파일 작성 후 빈 추가
  - 컨슈머 사용시 EnableKafka 어노테이션 필요

### Producer
- Config 에서 작성한 KafkaTemplate 을 주입받아 사용
- 전송 성공 시, 전송 실패 시에 대한 각각의 콜백 함수를 작성해 줄 수 있음

### Consumer
- KafkaListener 어노테이션을 활용하여 비교적 간단한 코드로 메시지의 구독이 가능
