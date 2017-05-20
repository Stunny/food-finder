CREATE TABLE IF NOT EXISTS userInfo(
    _id	INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    surname TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    gender INTEGER DEFAULT 0,
    description TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS restaurant (
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

CREATE TABLE IF NOT EXISTS recent_searches(
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    searchQuery TEXT NOT NULL,
    userId INT NOT NULL,
    FOREIGN KEY(userId) REFERENCES userInfo(_id)
);

CREATE TABLE IF NOT EXISTS favorite_restaurants(
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    userID INT NOT NULL,
    restaurantId INT NOT NULL,
    FOREIGN KEY(userID) REFERENCES userInfo(_id),
    FOREIGN KEY(restaurantId) REFERENCES restaurant(_id)
);
