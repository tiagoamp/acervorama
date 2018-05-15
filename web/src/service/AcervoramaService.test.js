import nock from 'nock';

import AcervoramaService from './AcervoramaService';

const service = new AcervoramaService();

const api = nock(service._API_BASE_URL);

describe('Acervorama Api calls', () => {
    
    it('should return all media items when request ok', async () => {
        expect.assertions(2);
        api.get(service._MEDIA_RESOURCE)
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
        api.get(service._MEDIA_RESOURCE).reply(400);

        try {
            await service.getMediaItems();
        } catch(e) {
            expect(e.message).toMatch('Error');
        }                
    });
       
    it('should return scanned list when request ok', async () => {
        expect.assertions(2);
        const params = {type: 'AUDIO', dirPath: '/home/musics'};
        api.get(service._SCANNER_RESOURCE)
            .query(params)
            .reply(200, [ '/home/musics/music1', '/home/musics/music2' ]);

        const data = await service.scanDirectory(params.type, params.dirPath);
        expect(data).not.toBeUndefined();        
        expect(data).toHaveLength(2);
    });

    it('should return error when client request fail', async () => {
        expect.assertions(1);
        const params = {type: 'AUDIO', dirPath: '/home/musics'};
        api.get(service._SCANNER_RESOURCE)
            .query(params)
            .reply(400);

        try {
            await service.scanDirectory(params.type, params.dirPath);
        } catch(e) {
            expect(e.message).toMatch('Bad parameters');
        }
    });

    it('should return error when server processing fail', async () => {
        expect.assertions(1);
        const params = {type: 'AUDIO', dirPath: '/home/musics'};
        api.get(service._SCANNER_RESOURCE)
            .query(params)
            .reply(500);

        try {
            await service.scanDirectory(params.type, params.dirPath);
        } catch(e) {
            expect(e.message).toMatch('Error');
        }
    });
      
    it('should save scanned medias when request ok', async () => {
        expect.assertions(2);
        const params = {type: 'AUDIO', filePath: '/home/musics'};
        const result = { resource: { type: 'AUDIO', id: 387, filename: 'music1.mp3' } };
        api.filteringRequestBody(/.*/, '*')
            .post(service._MEDIA_RESOURCE,'*')            
            .reply(201, result);

        const data = await service.saveScannedMedia(params.filePath, params.type);
        expect(data).not.toBeUndefined();        
        expect(data).toMatchObject(result);
    });

    it('should return error when file to be saved already exists', async () => {
        expect.assertions(1);
        const params = {type: 'AUDIO', filePath: '/home/musics'};
        api.filteringRequestBody(/.*/, '*')
            .post(service._MEDIA_RESOURCE,'*')            
            .reply(409);

        try {
            await service.saveScannedMedia(params.filePath, params.type);
        } catch(e) {
            expect(e.message).toMatch('File path already exists:');
        }        
    });

    it('should return error when request fail', async () => {
        expect.assertions(1);
        const params = {type: 'AUDIO', filePath: '/home/musics'};
        api.filteringRequestBody(/.*/, '*')
            .post(service._MEDIA_RESOURCE,'*')            
            .reply(500);

        try {
            await service.saveScannedMedia(params.filePath, params.type);
        } catch(e) {
            expect(e.message).toMatch('Error');
        }        
    });

}); 
