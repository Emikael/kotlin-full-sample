INSERT INTO public.address (id, cep, street, complement, neighborhood, city, state, ibge, created_at, updated_at)
VALUES (1, '88708-001', 'Rua São João', 'de 1610/1611 ao fim', 'São João (Margem Esquerda)', 'Tubarão', 'SC', 4218707,
        '2019-08-28 16:24:47.668000', '2019-08-28 18:04:58.090000');

INSERT INTO public.users (id, name, email, phone, cep, created_at, updated_at)
VALUES (1, 'Emikael Silveira', 'emikael.silveira@kotlin.com', '(88)3 3333-2222', '88708-001',
        '2019-08-28 11:02:55.001000', '2019-09-03 15:28:45.157000');
