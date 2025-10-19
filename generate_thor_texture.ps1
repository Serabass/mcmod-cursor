# Script for generating Thor hammer texture
$apiKey = "B5809AA3E98C78D494F9B7DAAB6876A3"
$secret = "BAE96EA23BDAED85277A1DAA42358EDA"

# Prepare request data
$body = @{
    "type" = "GENERATE"
    "numImages" = 1
    "width" = 512
    "height" = 512
    "generateParams" = @{
        "query" = "Thor hammer Mjolnir texture for Minecraft, metallic silver with blue lightning effects, Nordic runes, weathered metal surface, game asset style, 16x16 pixel art"
        "negativePrompt" = "blurry, low quality, photorealistic, 3d render"
        "style" = "ANIME"
    }
} | ConvertTo-Json -Depth 10

# Request headers
$headers = @{
    "X-Key" = "Key $apiKey"
    "X-Secret" = "Secret $secret"
    "Content-Type" = "application/json"
}

Write-Host "Sending request to generate Thor hammer texture..." -ForegroundColor Green

try {
    # Send request
    $response = Invoke-RestMethod -Uri "https://api-key.fusionbrain.ai/api/v1/text2image/run" -Method Post -Body $body -Headers $headers
    
    Write-Host "API Response:" -ForegroundColor Yellow
    $response | ConvertTo-Json -Depth 10 | Write-Host
    
    # Save UUID for status check
    $uuid = $response.uuid
    Write-Host "Task UUID: $uuid" -ForegroundColor Cyan
    
    # Wait for generation to complete
    Write-Host "Waiting for generation to complete..." -ForegroundColor Yellow
    Start-Sleep -Seconds 10
    
    # Check status
    $statusResponse = Invoke-RestMethod -Uri "https://api-key.fusionbrain.ai/api/v1/text2image/status/$uuid" -Method Get -Headers $headers
    Write-Host "Generation status:" -ForegroundColor Yellow
    $statusResponse | ConvertTo-Json -Depth 10 | Write-Host
    
    if ($statusResponse.status -eq "DONE") {
        Write-Host "Generation completed! Downloading image..." -ForegroundColor Green
        
        # Download image
        $imageUrl = $statusResponse.images[0]
        $imagePath = "src\main\resources\assets\cursor\textures\item\thor_hammer.png"
        
        Invoke-WebRequest -Uri $imageUrl -OutFile $imagePath
        Write-Host "Texture saved to: $imagePath" -ForegroundColor Green
    } else {
        Write-Host "Generation not completed yet. Status: $($statusResponse.status)" -ForegroundColor Yellow
    }
    
} catch {
    Write-Host "Error generating texture: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Error details: $($_.Exception)" -ForegroundColor Red
}
