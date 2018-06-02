const mongoose = require('mongoose');
const {Schema} = mongoose;

const highScoreSchema = new Schema({
    score: {
        type: Number,
        default: 0
    }
});

mongoose.model('highScore', highScoreSchema);