CREATE TABLE userInfo(
    _id	INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    surname TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    gender INTEGER DEFAULT 0,
    description TEXT NOT NULL
);

CREATE TABLE restaurant (
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    type TEXT NOT NULL,
    lat FLOAT DEFAULT 0,
    lng FLOAT DEFAULT 0,
    address TEXT NOT NULL,
    opening TEXT NOT NULL,--HH:MM
    closing TEXT NOT NULL,--HH:MM
    review FLOAT DEFAULT 0,
    description TEXT NOT NULL
);

CREATE TABLE favorite_restaurants(
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    userID INT FOREIGN KEY,
    restaurantId INT FOREIGN KEY
);

CREATE TABLE recent_searches(
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    searchQuery TEXT NOT NULL,
    userId INT FOREIGN KEY
);