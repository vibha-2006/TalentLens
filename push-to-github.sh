#!/bin/bash

echo "============================================"
echo "   TalentLens GitHub Push Script"
echo "============================================"
echo ""

# Check if GitHub CLI is installed
if command -v gh &> /dev/null; then
    echo "Using GitHub CLI to automate setup..."
    echo ""

    # Check authentication
    echo "Checking GitHub authentication..."
    if ! gh auth status &> /dev/null; then
        echo "Please authenticate with GitHub:"
        gh auth login
    fi

    echo ""
    echo "============================================"
    echo "Step 1: Creating GitHub Repository"
    echo "============================================"
    gh repo create TalentLens \
        --public \
        --description "AI-powered resume screening and candidate ranking system with multi-AI provider support (OpenAI, Gemini, Groq)" \
        --source=. \
        --remote=origin \
        --push=false || {
        echo "Repository might already exist. Checking..."
        if gh repo view TalentLens &> /dev/null; then
            echo "Repository exists. Adding remote..."
            git remote add origin https://github.com/$(gh api user -q .login)/TalentLens.git 2>/dev/null || true
        fi
    }

    echo ""
    echo "============================================"
    echo "Step 2: Pushing Main Branch"
    echo "============================================"
    git push -u origin main

    echo ""
    echo "============================================"
    echo "Step 3: Pushing Feature Branches"
    echo "============================================"
    git push origin feature/backend-api-enhancements
    git push origin feature/frontend-ui-improvements

    echo ""
    echo "============================================"
    echo "Step 4: Creating Pull Requests"
    echo "============================================"

    echo "Creating Backend PR..."
    gh pr create \
        --base main \
        --head feature/backend-api-enhancements \
        --title "feat(backend): add batch upload response DTO and enhance error handling" \
        --body-file PR_BACKEND.md \
        --label enhancement \
        --label backend

    echo ""
    echo "Creating Frontend PR..."
    gh pr create \
        --base main \
        --head feature/frontend-ui-improvements \
        --title "feat(frontend): add dark mode support and UI enhancements" \
        --body-file PR_FRONTEND.md \
        --label enhancement \
        --label frontend

    echo ""
    echo "============================================"
    echo "Success! All PRs Created"
    echo "============================================"
    echo ""
    echo "View your PRs: gh pr list"
    echo "View in browser: gh repo view --web"
    echo ""
else
    echo "GitHub CLI not found."
    echo ""
    echo "============================================"
    echo "Manual Setup Instructions"
    echo "============================================"
    echo ""
    echo "Please follow these steps:"
    echo ""
    echo "1. Create GitHub Repository:"
    echo "   - Go to: https://github.com/new"
    echo "   - Name: TalentLens"
    echo "   - Public repository"
    echo "   - Do NOT initialize with README"
    echo "   - Click 'Create repository'"
    echo ""
    echo "2. After creating, run these commands:"
    echo ""
    echo "   git remote add origin https://github.com/YOUR_USERNAME/TalentLens.git"
    echo "   git push -u origin main"
    echo "   git push origin feature/backend-api-enhancements"
    echo "   git push origin feature/frontend-ui-improvements"
    echo ""
    echo "3. Create PRs on GitHub:"
    echo "   - Go to: https://github.com/YOUR_USERNAME/TalentLens/pulls"
    echo "   - Click 'New pull request'"
    echo "   - Create PR from feature/backend-api-enhancements to main"
    echo "   - Create PR from feature/frontend-ui-improvements to main"
    echo ""
fi

