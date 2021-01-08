CREATE TABLE borrowing_history(
    borrowing_id INT NOT NULL AUTO_INCREMENT,
    user_id LONG NOT NULL,
    book_id LONG NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    return_date DATETIME,
    overdue_reason VARCHAR (1000),
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
    PRIMARY KEY (borrowing_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

