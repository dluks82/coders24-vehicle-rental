Feature: Cria Veiculo

  Scenario: Deve criar um veiculo
    Given O banco de dados deve estar vazio
    When Um carro com placa ABC1234, nome Honda Civic e tipo CAR deve ser criado
    Then Um carro com placa ABC1234, nome Honda Civic e tipo CAR deve ser encontrado