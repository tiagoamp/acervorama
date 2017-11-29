class MediaItem {

    constructor() {

        this._id;
        this._filePath;
        this._filename;
        this._registerDate;
        this._type;
        this._hash;
        this._tags;
        this._classification;
        this._description;
        this._additionalInformation;

    }

    get id() {
        return this._id;
    }
    get filePath() {
        return this._filePath;
    }
    get filename() {
        return this._filename;
    }
    get registerDate() {
        return this._filename;
    }
    get type() {
        return this._type;
    }
    get hash() {
        return this._hash;
    }
    get tags() {
        return this._tags;
    }
    get classification() {
        return this._classification;
    }
    get description() {
        return this._description;
    }
    get additionalInformation() {
        return this._additionalInformation;
    }

    set id(id) {
        this._id = id;
    }
    set filePath(value) {
        this._filePath = value;
    }
    set filename(value) {
        this._filename = value;
    }
    set registerDate(value) {
        this._filename = value;
    }
    set type(value) {
        this._type = value;
    }
    set hash(value) {
        this._hash = value;
    }
    set tags(value) {
        this._tags = value;
    }
    set classification(value) {
        this._classification = value;
    }
    set description(value) {
        this._description = value;
    }
    set additionalInformation(value) {
        this._additionalInformation = value;
    }

}