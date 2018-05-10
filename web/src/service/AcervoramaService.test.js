import fetch from 'node-fetch';
import AcervoramaService from './AcervoramaService';


const service = new AcervoramaService();


describe('Acervorama Api calls', () => {

    
    test('Should return all media items', async () => {
        expect.assertions(1);
        const data = await service.getMediaItems();
        expect(data).not.toBeUndefined();        
    });
    
    test('Should return media items if there are more than one resource', async () => {
        const data = await service.getMediaItems();
        expect(data).not.toBeUndefined();
        if (data.length > 0) {
            expect(data).toContainEqual(expect.objectContaining({resource: expect.anything()}));
        }
    });

    test('Should return empty list if there are no resources', async () => {
        const data = await service.getMediaItems();
        expect(data).not.toBeUndefined();
        if (data.length === 0) {
            expect(data).not.toBeNull();
        }
    });
      

}); 
