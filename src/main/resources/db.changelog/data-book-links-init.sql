insert into book_file (id, hash, type_id, path, book_id) values (1 , 'fsdl342ladads76432', 1, '/Virunga.pdf', 1);
insert into book_file (id, hash, type_id, path, book_id) values (2 , 'asdl35436dads34235', 2, '/Virunga.epub', 1);
insert into book_file (id, hash, type_id, path, book_id) values (3 , 'qwer342lafdss34123', 3, '/Virunga.fb2',  1);

insert into book2user_type (id, code, name) values (1, 'KEPT', 'Отложена');
insert into book2user_type (id, code, name) values (2, 'CART', 'В корзине');
insert into book2user_type (id, code, name) values (3, 'PAID', 'Куплена');
insert into book2user_type (id, code, name) values (4, 'ARCHIVED', 'В архиве');

insert into book_file_type (id, name, description) values (1, 'PDF', 'PDF - это широко используемый формат файлов, разработанный Adobe в 1992 году. Он используется во всех видах бизнеса для представления и обмена документами. Файл PDF содержит все элементы печатного документа в виде электронного изображения. Помимо текста и изображений, PDF-файлы также могут содержать гиперссылки, встроенные шрифты, видео, кнопки и многое другое.');
insert into book_file_type (id, name, description) values (2, 'EPUB', 'EPUB - это формат файла электронной книги, который использует расширение файла ".epub". Этот термин является сокращением для обозначения электронной публикации и иногда называется ePub.');
insert into book_file_type (id, name, description) values (3, 'FB2', 'Файл с расширением FB2 - это файл электронной книги FictionBook. Формат был создан с учетом художественных произведений, но, конечно, может быть использован для хранения любого типа электронных книг. ');
