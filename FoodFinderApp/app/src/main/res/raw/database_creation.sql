CREATE TABLE userInfo(
    _id	INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    surname TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    gender INTEGER DEFAULT 0,
    description TEXT NOT NULL
);

CREATE TABLE restaurant(
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    imgURI TEXT,
    address TEXT NOT NULL,
    lat DOUBLE NOT NULL,
    lng DOUBLE NOT NULL,
    -- Formato de tiempo HH:mm
    openingTime TEXT NOT NULL,
    closingTime TEXT NOT NULL,

    rating FLOAT,
    description TEXT
);

CREATE TABLE favorite_restaurants(
    userID INTEGER PRIMARY KEY,
    restaurantID INTEGER PRIMARY KEY
);

CREATE TABLE recent_places(
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    restaurantID INTEGER NOT NULL,
    userID INTEGER NOT NULL,
    -- Formato de fecha ISO8601:"YYYY-MM-DD HH:MM:SS.SSS"
    date TEXT NOT NULL
);

CREATE TABLE comment(
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    restaurantID INTEGER NOT NULL,
    userID INTEGER NOT NULL,
    content TEXT NOT NULL
);