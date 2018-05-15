import nock from 'nock';

import AcervoramaService from './AcervoramaService';
import { config } from './config';

const service = new AcervoramaService();
const apiconfig = config;

const api = nock(config.API_BASE_URL);


describe('Acervorama Api calls', () => {
     
    it('should return all media items when request ok', async () => {
        expect.assertions(2);
        api.get(config.MEDIA_RESOURCE)
            .reply(200, 
                [ 
                    { resource: { type: 'AUDIO', id: 387, filename: 'music1.mp3' } },
                    { resource: { type: 'AUDIO', id: 388, filename: 'music2.mp3' } }  
                ]
            );
        const data = await service.getMediaItems();        
        expect(data).not.toBeUndefined();         
        expect(data).toHaveLength(2);
    });

    it('should return error when request fail', async () => {
        expect.assertions(1);
        api.get(config.MEDIA_RESOURCE).reply(400);

        try {
            await service.getMediaItems();
        } catch(e) {
            expect(e.message).toMatch('Error');
        }                
    });
       
    it('should return scanned list when request ok', async () => {
        expect.assertions(2);
        const params = {type: 'AUDIO', dirPath: '/home/musics'};
        api.get(config.SCANNER_RESOURCE)
            .query(params)
            .reply(200, [ '/home/musics/music1', '/home/musics/music2' ]);

        const data = await service.scanDirectory(params.type, params.dirPath);
        expect(data).not.toBeUndefined();        
        expect(data).toHaveLength(2);
    });

    it('should return error when client request fail', async () => {
        expect.assertions(1);
        const params = {type: 'AUDIO', dirPath: '/home/musics'};
        api.get(config.SCANNER_RESOURCE).query(params).reply(400);

        try {
            await service.scanDirectory(params.type, params.dirPath);
        } catch(e) {
            expect(e.message).toMatch('Bad parameters');
        }
    });

    it('should return error when server processing fail', async () => {
        expect.assertions(1);
        const params = {type: 'AUDIO', dirPath: '/home/musics'};
        api.get(config.SCANNER_RESOURCE).query(params).reply(500);

        try {
            await service.scanDirectory(params.type, params.dirPath);
        } catch(e) {
            expect(e.message).toMatch('Error');
        }
    });
      
    it('should save scanned medias when request ok', async () => {
        expect.assertions(1);
        const params = {type: 'AUDIO', filePath: '/home/musics'};
        const result = { resource: { type: 'AUDIO', id: 387, filename: 'music1.mp3' } };
        api.filteringRequestBody(/.*/, '*')
            .post(config.MEDIA_RESOURCE,'*')            
            .reply(201, result);

        const data = await service.saveScannedMedia(params.filePath, params.type);
        expect(data).toMatchObject(result);
    });

    it('should return error when file to be saved already exists', async () => {
        expect.assertions(1);
        const params = {type: 'AUDIO', filePath: '/home/musics'};
        api.post(config.MEDIA_RESOURCE,'*').reply(409);

        try {
            await service.saveScannedMedia(params.filePath, params.type);
        } catch(e) {
            expect(e.message).toMatch('File path already exists:');
        }        
    });

    it('should return error when request fail', async () => {
        expect.assertions(1);
        const params = {type: 'AUDIO', filePath: '/home/musics'};
        api.post(config.MEDIA_RESOURCE,'*').reply(500);

        try {
            await service.saveScannedMedia(params.filePath, params.type);
        } catch(e) {
            expect(e.message).toMatch('Error');
        }        
    });

    it('should return media item when request ok', async () => {
        expect.assertions(1);
        const params = {filename: 'music1', classification: 'classif', type: 'AUDIO', tags: 'TAG01,TAG02'};        
        const result = { resource: { type: 'AUDIO', id: 387, filename: 'music1.mp3' } };
        api.get(config.MEDIA_RESOURCE).query(params)
            .reply(200, result);

        const data = await service.searchMediaItems(params.filename, params.classification, params.type, params.tags);
        expect(data).toMatchObject(result);
    });

    it('should return Error when request fail', async () => {
        expect.assertions(1);
        const params = {filename: 'music1', classification: 'classif', type: 'AUDIO', tags: 'TAG01,TAG02'};        
        api.get(config.MEDIA_RESOURCE).query(params)
            .reply(500);

        try {
            await service.searchMediaItems(params.filename, params.classification, params.type, params.tags);
        } catch(e) {
            expect(e.message).toMatch('Error');
        }
    });

    it('should update media file when request ok', async () => {
        const media = {id: '1', type: 'AUDIO', filePath: '/home/musics'};
        api.put(config.MEDIA_RESOURCE + "/" + media.id).reply(200);

        try {
            await service.updateMediaItem(media);
        } catch(e) {
            expect(e).toBeUndefined();
        }
    });

    it('should return error when client request fail', async () => {
        expect.assertions(1);
        const media = {id: '1', type: 'AUDIO', filePath: '/home/musics'};
        api.put(config.MEDIA_RESOURCE + "/" + media.id).reply(404);

        try {
            await service.updateMediaItem(media);
        } catch(e) {
            expect(e.message).toMatch('File path do not exists:');
        }
    });

    it('should return error when request processing fail', async () => {
        expect.assertions(1);
        const media = {id: '1', type: 'AUDIO', filePath: '/home/musics'};
        api.put(config.MEDIA_RESOURCE + "/" + media.id).reply(500);

        try {
            await service.updateMediaItem(media);
        } catch(e) {
            expect(e.message).toMatch('Error');
        }
    });

    it('should delete media file when request ok', async () => {
        const media = {id: '1', type: 'AUDIO', filePath: '/home/musics'};
        api.delete(config.MEDIA_RESOURCE + "/" + media.id).reply(204);

        try { 
            await service.deleteMediaItem(media);
        } catch(e) {
            expect(e).toBeUndefined();
        }
    });

    it('should return error when requests fail', async () => {
        const media = {id: '1', type: 'AUDIO', filePath: '/home/musics'};
        api.delete(config.MEDIA_RESOURCE + "/" + media.id).reply(500);

        try {
            await service.deleteMediaItem(media);
        } catch(e) {
            expect(e.message).toMatch('Error');
        }
    });

}); 
