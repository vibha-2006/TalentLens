# Admin Settings Screen Preview

## Visual Layout

```
╔═══════════════════════════════════════════════════════════════════════════╗
║                         🎯 TalentLens                                     ║
║              AI-Powered Resume Shortlisting with Multiple Providers       ║
╠═══════════════════════════════════════════════════════════════════════════╣
║ ┌───────────────┐ ┌────────────────────────────────────────────────────┐ ║
║ │               │ │                                                     │ ║
║ │ Job Req       │ │  🔧 Admin Settings - AI Provider Configuration     │ ║
║ │               │ │                                                     │ ║
║ │ Upload        │ │  Configure API keys and settings for AI providers  │ ║
║ │               │ │                                                     │ ║
║ │ Rankings      │ │  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  │ ║
║ │               │ │  ┃          OpenAI          [Configured ✓]    ┃  │ ║
║ │ ▶ Admin ◀     │ │  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  │ ║
║ │               │ │  ┃ API Key: sk-p***************Z04oA          ┃  │ ║
║ │               │ │  ┃ [Edit]                                     ┃  │ ║
║ │               │ │  ┃                                             ┃  │ ║
║ │               │ │  ┃ Model: gpt-3.5-turbo                       ┃  │ ║
║ │               │ │  ┃                                             ┃  │ ║
║ │               │ │  ┃ API URL: https://api.openai.com/v1/...     ┃  │ ║
║ │               │ │  ┃                                             ┃  │ ║
║ │               │ │  ┃ [Test Connection]                          ┃  │ ║
║ │               │ │  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  │ ║
║ │               │ │                                                     │ ║
║ │               │ │  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  │ ║
║ │               │ │  ┃       Google Gemini       [Configured ✓]  ┃  │ ║
║ │               │ │  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  │ ║
║ │               │ │  ┃ API Key: AIza***************k-cuQbBM       ┃  │ ║
║ │               │ │  ┃ [Edit]                                     ┃  │ ║
║ │               │ │  ┃                                             ┃  │ ║
║ │               │ │  ┃ Model: gemini-1.5-flash                    ┃  │ ║
║ │               │ │  ┃                                             ┃  │ ║
║ │               │ │  ┃ API URL: https://generativelanguage...     ┃  │ ║
║ │               │ │  ┃                                             ┃  │ ║
║ │               │ │  ┃ [Test Connection]                          ┃  │ ║
║ │               │ │  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  │ ║
║ │               │ │                                                     │ ║
║ │               │ │  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  │ ║
║ │               │ │  ┃           Groq            [Configured ✓]  ┃  │ ║
║ │               │ │  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  │ ║
║ │               │ │  ┃ API Key: gsk_***************s84Kdf9s       ┃  │ ║
║ │               │ │  ┃ [Edit]                                     ┃  │ ║
║ │               │ │  ┃                                             ┃  │ ║
║ │               │ │  ┃ Model: llama-3.3-70b-versatile             ┃  │ ║
║ │               │ │  ┃                                             ┃  │ ║
║ │               │ │  ┃ API URL: https://api.groq.com/openai/v1... ┃  │ ║
║ │               │ │  ┃                                             ┃  │ ║
║ │               │ │  ┃ [Test Connection]                          ┃  │ ║
║ │               │ │  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  │ ║
║ │               │ │                                                     │ ║
║ │               │ │  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  │ ║
║ │               │ │  ┃ 📚 Help & Documentation                    ┃  │ ║
║ │               │ │  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  │ ║
║ │               │ │  ┃ OpenAI                                     ┃  │ ║
║ │               │ │  ┃ Get key: platform.openai.com/api-keys      ┃  │ ║
║ │               │ │  ┃                                             ┃  │ ║
║ │               │ │  ┃ Google Gemini                              ┃  │ ║
║ │               │ │  ┃ Get key: makersuite.google.com/app/apikey  ┃  │ ║
║ │               │ │  ┃ Enable: Generative Language API in GCP     ┃  │ ║
║ │               │ │  ┃                                             ┃  │ ║
║ │               │ │  ┃ Groq                                       ┃  │ ║
║ │               │ │  ┃ Get key: console.groq.com/keys             ┃  │ ║
║ │               │ │  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  │ ║
║ │               │ │                                                     │ ║
║ │               │ │  ⚠️ Important: Restart backend after saving        │ ║
║ │               │ │                                                     │ ║
║ └───────────────┘ └────────────────────────────────────────────────────┘ ║
╠═══════════════════════════════════════════════════════════════════════════╣
║              Powered by OpenAI, Gemini & Groq | TalentLens © 2024        ║
╚═══════════════════════════════════════════════════════════════════════════╝
```

## Edit Mode Example

When clicking "Edit" on OpenAI card:

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃          OpenAI          [Configured ✓]    ┃
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫
┃ API Key:                                   ┃
┃ [••••••••••••••••••••••••••••••••••]       ┃
┃ (Enter new API key or leave blank)         ┃
┃                                             ┃
┃ Model:                                     ┃
┃ [▼ gpt-3.5-turbo      ▼]                  ┃
┃    • gpt-4o                                ┃
┃    • gpt-4o-mini                           ┃
┃    • gpt-4-turbo                           ┃
┃    • gpt-4                                 ┃
┃    ▶ gpt-3.5-turbo                         ┃
┃                                             ┃
┃ API URL:                                   ┃
┃ [https://api.openai.com/v1/chat/completions]┃
┃                                             ┃
┃ [   Save   ]  [  Cancel  ]                 ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

## Success Message Example

After saving settings:

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ ✓ OPENAI settings saved successfully!                   ┃
┃   Please restart the backend for changes to take full   ┃
┃   effect.                                                ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

## Test Connection Example

After clicking "Test Connection":

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ ✓ OPENAI: API key is configured                        ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

## Color Scheme

- **Headers**: Purple gradient (667eea → 764ba2)
- **Status Badge (Configured)**: Green (#28a745)
- **Status Badge (Not Configured)**: Gray (#6c757d)
- **Success Messages**: Light green background (#d4edda)
- **Error Messages**: Light red background (#f8d7da)
- **Warning Messages**: Light yellow background (#fff3cd)
- **Buttons**: 
  - Primary (Save): Purple gradient
  - Secondary (Cancel): Gray (#6c757d)
  - Test: Cyan (#17a2b8)
  - Edit: Purple (#667eea)

## Responsive Design

### Desktop (1200px+)
- Three provider cards side by side in grid layout
- Full width controls and inputs
- Help section spans full width below cards

### Tablet (768px - 1199px)
- Two provider cards per row
- Adjusted spacing and padding

### Mobile (<768px)
- Single column layout
- Stack all cards vertically
- Full width buttons
- Touch-friendly sizing

## Interactive Elements

1. **Hover Effects**
   - Cards lift slightly on hover
   - Buttons darken/lighten on hover
   - Links underline on hover

2. **Active States**
   - Navigation tab highlighted when selected
   - Input fields show focus border
   - Buttons show pressed state

3. **Disabled States**
   - Test button disabled when provider not configured
   - Save/Cancel buttons disabled during API calls
   - Grayed out appearance for disabled elements

## Accessibility Features

- Semantic HTML structure
- Keyboard navigation support
- Focus indicators
- ARIA labels where appropriate
- Color contrast meets WCAG standards
- Clear error messages
- Form validation feedback

## Browser Compatibility

✅ Chrome 90+
✅ Firefox 88+
✅ Safari 14+
✅ Edge 90+
✅ Opera 76+

---

This admin settings screen provides an intuitive, professional interface for managing AI provider configurations without editing files manually!

