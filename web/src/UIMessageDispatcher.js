import Noty from 'noty';

export default class UIMessageDispatcher {

    static showSuccessMessage(msg) {
        return new Noty({
            type: 'success',
            layout: 'centerRight',
            //killer: true,
            timeout: 3000,
            text: msg
        }).show();
    }

    static showErrorMessage(msg) {        
        return new Noty({
            type: 'error',
            layout: 'centerRight',
            //killer: true,
            timeout: 3000,
            text: msg
        }).show();
    }

    static showInfoMessage(msg) {
        return new Noty({
            type: 'info',
            layout: 'centerRight',
            //killer: true,
            timeout: 3000,
            text: msg
        }).show();
    }
    
}
