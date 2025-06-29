package com.skillswap.server.utils;

import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

    public String getSkillsSwapHomePage() {
        return """
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Chào mừng đến với SkillsSwap - Trao đổi kỹ năng, Kết nối cộng đồng</title>
  <style>
    * { margin: 0; padding: 0; box-sizing: border-box; }
    body {
      font-family: 'Arial', sans-serif;
      background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 50%, #A855F7 100%);
      min-height: 100vh;
      overflow-x: hidden;
    }
    .hero-section {
      min-height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
      position: relative;
      padding: 20px;
    }
    .hero-background {
      position: absolute;
      top: 0; left: 0;
      width: 100%; height: 100%;
      background: none;
      opacity: 0.6;
    }
    .welcome-container {
      max-width: 1200px;
      width: 100%;
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 60px;
      align-items: center;
      position: relative;
      z-index: 2;
    }
    .welcome-content { color: white; }
    .welcome-badge {
      background: rgba(255,255,255,0.2);
      backdrop-filter: blur(10px);
      border: 1px solid rgba(255,255,255,0.3);
      padding: 12px 24px;
      border-radius: 50px;
      display: inline-flex;
      align-items: center;
      gap: 10px;
      margin-bottom: 30px;
      font-size: 0.95em;
      font-weight: 500;
    }
    .welcome-title {
      font-size: 3.5em;
      font-weight: bold;
      margin-bottom: 20px;
      line-height: 1.1;
      color: white;
    }
    .welcome-subtitle {
      font-size: 1.3em;
      margin-bottom: 30px;
      opacity: 0.9;
      line-height: 1.6;
    }
    .cta-buttons {
      display: flex;
      gap: 20px;
      margin-bottom: 40px;
    }
    .btn-primary {
      background: white;
      color: #8B5CF6;
      padding: 15px 35px;
      border: none;
      border-radius: 50px;
      font-size: 1.1em;
      font-weight: 600;
      cursor: pointer;
    }
    .btn-secondary {
      background: rgba(255,255,255,0.1);
      color: white;
      padding: 15px 35px;
      border: 2px solid rgba(255,255,255,0.3);
      border-radius: 50px;
      font-size: 1.1em;
      font-weight: 600;
      cursor: pointer;
      backdrop-filter: blur(10px);
    }
    .stats-row {
      display: flex;
      gap: 30px;
      margin-top: 40px;
    }
    .stat-item { text-align: center; }
    .stat-number {
      font-size: 2.5em;
      font-weight: bold;
      color: white;
    }
    .stat-label {
      font-size: 0.9em;
      opacity: 0.8;
      margin-top: 5px;
    }
    .welcome-visual { position: relative; }
    .visual-container {
      background: rgba(255,255,255,0.1);
      backdrop-filter: blur(20px);
      border: 1px solid rgba(255,255,255,0.2);
      border-radius: 30px;
      padding: 40px;
      position: relative;
    }
    .skill-cards {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 20px;
      margin-bottom: 30px;
    }
    .skill-card {
      background: rgba(255,255,255,0.9);
      border-radius: 15px;
      padding: 20px;
      color: #6B46C1;
      cursor: pointer;
    }
    .skill-icon { font-size: 2em; margin-bottom: 10px; }
    .skill-title { font-weight: bold; margin-bottom: 5px; }
    .skill-desc { font-size: 0.85em; opacity: 0.7; }
    .connection-visual {
      position: relative;
      height: 100px;
      margin-top: 20px;
    }
    .connection-line {
      position: absolute;
      top: 50%; left: 0; right: 0;
      height: 2px;
      background: rgba(255,255,255,0.5);
    }
    .connection-dot {
      position: absolute;
      width: 12px;
      height: 12px;
      background: white;
      border-radius: 50%;
      top: 50%;
      transform: translateY(-50%);
    }
    .connection-dot:nth-child(2) { left: 25%; }
    .connection-dot:nth-child(3) { left: 50%; }
    .connection-dot:nth-child(4) { left: 75%; }
    .features-section {
      background: white;
      padding: 80px 20px;
    }
    .features-container {
      max-width: 1200px;
      margin: 0 auto;
    }
    .features-title {
      text-align: center;
      font-size: 3em;
      color: #6B46C1;
      margin-bottom: 20px;
      font-weight: bold;
    }
    .features-subtitle {
      text-align: center;
      font-size: 1.2em;
      color: #6B7280;
      margin-bottom: 60px;
      max-width: 600px;
      margin-left: auto;
      margin-right: auto;
    }
    .features-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
      gap: 40px;
    }
    .feature-card {
      background: linear-gradient(135deg, #8B5CF6, #6366F1);
      color: white;
      padding: 40px;
      border-radius: 20px;
      text-align: center;
    }
    .feature-icon { font-size: 3em; margin-bottom: 20px; }
    .feature-title { font-size: 1.5em; font-weight: bold; margin-bottom: 15px; }
    .feature-desc { font-size: 1em; opacity: 0.9; line-height: 1.6; }
    .cta-section {
      background: linear-gradient(135deg, #6B46C1, #8B5CF6);
      padding: 80px 20px;
      text-align: center;
      color: white;
    }
    .cta-container {
      max-width: 800px;
      margin: 0 auto;
    }
    .cta-title {
      font-size: 2.5em;
      font-weight: bold;
      margin-bottom: 20px;
    }
    .cta-text {
      font-size: 1.2em;
      margin-bottom: 40px;
      opacity: 0.9;
    }
    .cta-button {
      background: white;
      color: #6B46C1;
      padding: 20px 50px;
      border: none;
      border-radius: 50px;
      font-size: 1.2em;
      font-weight: bold;
      cursor: pointer;
    }
  </style>
</head>
<body>
  <section class="hero-section">
    <div class="hero-background"></div>
    <div class="welcome-container">
      <div class="welcome-content">
        <div class="welcome-badge">✨ Chào mừng đến với tương lai của việc học tập</div>
        <h1 class="welcome-title">Chào mừng đến với <br>SkillsSwap</h1>
        <p class="welcome-subtitle">
          Kết nối với những người tài năng trên toàn thế giới. Trao đổi kỹ năng, chia sẻ kiến thức và xây dựng mối quan hệ chuyên nghiệp ý nghĩa.
        </p>
        <div class="cta-buttons">
          <button class="btn-primary">🚀 Bắt đầu ngay</button>
          <button class="btn-secondary">📖 Tìm hiểu thêm</button>
        </div>
        <div class="stats-row">
          <div class="stat-item"><span class="stat-number">10K+</span><span class="stat-label">Thành viên hoạt động</span></div>
          <div class="stat-item"><span class="stat-number">500+</span><span class="stat-label">Kỹ năng được chia sẻ</span></div>
          <div class="stat-item"><span class="stat-number">95%</span><span class="stat-label">Tỷ lệ thành công</span></div>
        </div>
      </div>
      <div class="welcome-visual">
        <div class="visual-container">
          <div class="skill-cards">
            <div class="skill-card"><div class="skill-icon">💻</div><div class="skill-title">Lập trình</div><div class="skill-desc">Phát triển Web</div></div>
            <div class="skill-card"><div class="skill-icon">🎨</div><div class="skill-title">Thiết kế</div><div class="skill-desc">UI/UX</div></div>
            <div class="skill-card"><div class="skill-icon">📈</div><div class="skill-title">Marketing</div><div class="skill-desc">Marketing số</div></div>
            <div class="skill-card"><div class="skill-icon">🎵</div><div class="skill-title">Âm nhạc</div><div class="skill-desc">Dạy đàn guitar</div></div>
          </div>
          <div class="connection-visual">
            <div class="connection-line"></div>
            <div class="connection-dot"></div>
            <div class="connection-dot"></div>
            <div class="connection-dot"></div>
            <div class="connection-dot"></div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <section class="features-section">
    <div class="features-container">
      <h2 class="features-title">Vì sao chọn SkillsSwap?</h2>
      <p class="features-subtitle">Khám phá những tính năng mạnh mẽ giúp việc trao đổi kỹ năng trở nên dễ dàng, an toàn và đầy cảm hứng.</p>
      <div class="features-grid">
        <div class="feature-card"><div class="feature-icon">🤝</div><h3 class="feature-title">Trao đổi công bằng</h3><p class="feature-desc">Hệ thống tín dụng giúp bạn dạy những gì bạn biết và học những gì bạn cần.</p></div>
        <div class="feature-card"><div class="feature-icon">🛡️</div><h3 class="feature-title">Hồ sơ xác thực</h3><p class="feature-desc">Tất cả thành viên được xác minh kỹ năng và đánh giá để đảm bảo chất lượng học tập.</p></div>
        <div class="feature-card"><div class="feature-icon">🌍</div><h3 class="feature-title">Cộng đồng toàn cầu</h3><p class="feature-desc">Kết nối với người học và giảng viên toàn thế giới. Khám phá văn hóa và kỹ năng mới.</p></div>
      </div>
    </div>
  </section>
  <section class="cta-section">
    <div class="cta-container">
      <h2 class="cta-title">Sẵn sàng bắt đầu hành trình của bạn?</h2>
      <p class="cta-text">Tham gia cùng hàng ngàn người đang nâng cao kỹ năng và sự nghiệp với SkillsSwap.</p>
      <button class="cta-button">🎯 Tạo hồ sơ của bạn</button>
    </div>
  </section>
</body>
</html>
    """;
    }

    public String getWelcomeEmailSubject() {
        return "Chào mừng bạn đến với SkillsSwap!";
    }

}
