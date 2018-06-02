require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const app = express();

const basicAuth = require('express-basic-auth');
const {mongoURI, userName, password} = require('./config/keys');
mongoose.connect(`mongodb://${userName}:${password}@${mongoURI}`).catch(error => console.log(error));

const users = {};
users[userName] = password;
app.use(basicAuth({users}));

require('./models/HighScore');
const HighScore = mongoose.model('highScore');

app.get('/highScore', async (req, res) => {

   const {score} = await HighScore.findOne({});
   res.send(score + "");
});

app.post('/highScore/:score', async (req, res) => {
    const {score} = req.params;
    const highScore = await HighScore.findOne({});
    if (!highScore) {
        await new HighScore({score}).save();
    }
    else {
        highScore.score = score;
        await HighScore.updateOne({}, highScore);
    }
    res.send('OK');
});

app.listen(process.env.PORT || 8080);