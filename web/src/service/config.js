const apiBaseUrl = 'http://localhost:8080';
const mediaResource = '/media';
const scannerResource = '/scanner';

const config = {
    API_BASE_URL: apiBaseUrl,
    MEDIA_RESOURCE: mediaResource, 
    SCANNER_RESOURCE: scannerResource, 
    MEDIA_API_URL: apiBaseUrl + mediaResource,
    SCANNER_API_URL: apiBaseUrl + scannerResource    
};

module.exports.config = config;